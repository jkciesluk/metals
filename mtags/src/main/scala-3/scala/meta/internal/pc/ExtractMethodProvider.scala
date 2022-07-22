package scala.meta.internal.pc

import java.net.URI
import java.nio.file.Paths

import scala.annotation.tailrec
import scala.meta as m

import scala.meta.internal.mtags.MtagsEnrichments.*
import scala.meta.internal.pc.AutoImports.AutoImportsGenerator
import scala.meta.internal.pc.printer.DotcPrinter
import scala.meta.internal.pc.printer.MetalsPrinter
import scala.meta.pc.OffsetParams
import scala.meta.pc.PresentationCompilerConfig
import scala.meta.pc.SymbolSearch

import dotty.tools.dotc.ast.Trees.*
import dotty.tools.dotc.ast.tpd
import dotty.tools.dotc.ast.untpd
import dotty.tools.dotc.core.Contexts.*
import dotty.tools.dotc.core.Names.TermName
import dotty.tools.dotc.core.Types.Type
import dotty.tools.dotc.interactive.Interactive
import dotty.tools.dotc.interactive.InteractiveDriver
import dotty.tools.dotc.util.SourceFile
import dotty.tools.dotc.util.SourcePosition
import org.eclipse.lsp4j.Position
import org.eclipse.lsp4j.TextEdit
import org.eclipse.{lsp4j as l}

final class ExtractMethodProvider(
    params: OffsetParams,
    applRange: Int,
    lv: Int,
    driver: InteractiveDriver,
    config: PresentationCompilerConfig,
    symbolSearch: SymbolSearch,
):

  def extractMethod(): List[TextEdit] =
    val uri = params.uri
    val filePath = Paths.get(uri)
    val source = SourceFile.virtual(filePath.toString, params.text)
    driver.run(uri, source)
    val unit = driver.currentCtx.run.units.head
    val startPos = driver.sourcePosition(params)
    val pos = startPos.withEnd(startPos.end + applRange)
    val path =
      Interactive.pathTo(driver.openedTrees(uri), pos)(using driver.currentCtx)
    given locatedCtx: Context = driver.localContext(params)
    val indexedCtx = IndexedContext(locatedCtx)

    def isBlockOrTemplate(t: tpd.Tree): Boolean =
      t match
        case temp @ Template(_, _, _, _) => true
        case block @ Block(_, expr: Closure[?]) => false
        case block @ Block(_, _) => true
        case _ => false

    def statsInBlock(t: tpd.Tree): List[tpd.Tree] =
      t match
        case temp @ Template(_, _, _, preBody) => temp.body
        case block @ Block(stats, expr) => stats :+ expr
        case _ => Nil

    val printer = MetalsPrinter.standard(
      indexedCtx,
      symbolSearch,
      includeDefaultParam = MetalsPrinter.IncludeDefaultParam.ResolveLater,
    )

    def genName(ts: Seq[tpd.Tree]): String =
      val names = ts
        .flatMap(
          _ match
            case dl @ DefDef(name, _, _, _) => Some(name.toString)
            case _ => None
        )
        .toSet
      if !names("newMethod") then "newMethod"
      else
        Range(0, 10)
          .map(i => s"newMethod$i")
          .find(!names.contains(_))
          .getOrElse("newMethod")
    end genName

    def localVariables(ts: Seq[tpd.Tree]): Seq[(TermName, String)] =
      ts.flatMap(
        _ match
          case vl @ ValDef(sym, tpt, _) => Seq((sym, printer.tpe(tpt.tpe)))

          case b @ Block(stats, expr) => localVariables(stats :+ expr)
          case t @ Template(_, _, _, _) =>
            localVariables(t.body)
          case _ => Nil
      )

    def namesInVal(t: tpd.Tree): Set[TermName] =
      t match
        case Apply(fun, args) =>
          namesInVal(fun) ++ args.flatMap(namesInVal(_)).toSet
        case TypeApply(fun, args) =>
          namesInVal(fun) ++ args.flatMap(namesInVal(_)).toSet
        case Select(qualifier, name) => namesInVal(qualifier) + name.toTermName
        case Ident(name) => Set(name.toTermName)
        case _ => Set()

    val edits =
      for
        apply <- path.headOption
        blocks = path.filter(isBlockOrTemplate)
        block = blocks(lv) if blocks.length >= lv
        stats = statsInBlock(block)
        firstStat <- stats.headOption
        stat <- stats.find(_.endPos.end >= apply.endPos.end)
      yield
        val namesInAppl = namesInVal(apply)
        val text = params.text()
        val indent = firstStat.startPos.startColumn
        val blank =
          if source(stat.startPos.start - indent) == '\t'
          then "\t"
          else " "
        val locals = localVariables(
          path.take(path.indexOf(block))
        ).reverse.toMap
        val withType =
          locals.filter((key, tp) => namesInAppl.contains(key)).toList.sorted
        val typs = withType
          .map((k, v) => s"$k: $v")
          .mkString(", ")
        val applType = printer.tpe(apply.tpe)
        val applParams = withType.map(_._1).mkString(", ")
        val name = genName(stats)
        val defText = s"${blank * indent}def $name($typs): $applType = ${text
            .slice(apply.startPos.start, apply.endPos.end)}\n"
        val replacedText = s"$name($applParams)"
        val defPos = Position(new Integer(stat.startPos.startLine), 0)
        List(
          new l.TextEdit(
            l.Range(
              apply.startPos.toLSP.getStart(),
              apply.endPos.toLSP.getEnd(),
            ),
            replacedText,
          ),
          new l.TextEdit(
            l.Range(defPos, defPos),
            defText,
          ),
        )

    edits.getOrElse(Nil)
  end extractMethod
end ExtractMethodProvider
