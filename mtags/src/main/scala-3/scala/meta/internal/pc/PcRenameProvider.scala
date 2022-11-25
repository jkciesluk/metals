package scala.meta.internal.pc
import scala.meta.internal.mtags.MtagsEnrichments.*
import scala.meta.pc.OffsetParams

import dotty.tools.dotc.ast.tpd.*
import dotty.tools.dotc.interactive.InteractiveDriver
import dotty.tools.dotc.util.SourcePosition
import org.eclipse.{lsp4j as l}
import java.nio.file.Paths
import dotty.tools.dotc.util.SourceFile
import dotty.tools.dotc.core.Contexts.Context
import dotty.tools.dotc.interactive.Interactive
import dotty.tools.dotc.core.Symbols.NoSymbol
import dotty.tools.dotc.core.Names.*
import dotty.tools.dotc.core.StdNames.*
import dotty.tools.dotc.core.Symbols.Symbol
import dotty.tools.dotc.core.Flags.*

object PcRenameProvider:

end PcRenameProvider

final class PcRenameProvider(
    driver: InteractiveDriver,
    params: OffsetParams,
    name: Option[String],
) extends PcCollector[l.TextEdit](driver, params):

  def canRenameSymbol(sym: Symbol)(using Context): Boolean =
    pprint.log(sym.ownersIterator.toList.map(s => (s, s.name.decoded)))
    val forbiddenMethods = Set("equals", "hashCode", "unapply", "unary_!", "!")
    (!sym.is(Method) || !forbiddenMethods(sym.decodedName))
    && sym.ownersIterator
      .drop(1)
      .exists(ow =>
        ow.is(Method) || (ow.is(ModuleClass)) && ow.name.decoded == "worksheet"
      )
    && sym.isDefinedInCurrentRun

  def prepareRename(): Option[l.Range] =
    soughtSymbols(path).flatMap((symbols, pos) =>
      if symbols.forall(canRenameSymbol) then Some(pos.toLsp)
      else None
    )

  val newName = name.map(_.stripBackticks.backticked).getOrElse("newName")

  def collect(tree: Tree, toAdjust: SourcePosition): l.TextEdit =
    val (pos, stripBackticks) = adjust(toAdjust)
    l.TextEdit(
      pos.toLsp,
      if stripBackticks then newName.stripBackticks else newName,
    )
  end collect

  def rename(
  ): List[l.TextEdit] =
    val (symbols, _) = soughtSymbols(path).getOrElse(Set.empty, pos)
    if symbols.nonEmpty && symbols.forall(canRenameSymbol(_))
    then
      val res = result()
      res
    else Nil
  end rename
end PcRenameProvider
