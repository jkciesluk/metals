package scala.meta.internal.pc.completions

import scala.meta.internal.mtags.CoursierComplete
import scala.meta.internal.pc.MetalsGlobal

import org.eclipse.{lsp4j => l}

trait SbtLibCompletions {
  this: MetalsGlobal =>

  object SbtLibExtractor {
    def unapply(path: List[Tree]) = {
      path match {
        case (lt @ Literal(art: Constant)) :: Apply(
              Select(Literal(src: Constant), name: Name),
              _
            ) :: _
            if Set("%", "%%").contains(name.decoded) &&
              src.tag == StringTag && art.tag == StringTag =>
          val depString =
            src.stringValue +
              name.decoded.replace('%', ':') +
              art.stringValue
          Some((lt.pos, depString))

        case (lt @ Literal(ver: Constant)) :: Apply(
              Select(
                Apply(
                  Select(Literal(src: Constant), name: Name),
                  List(Literal(art: Constant))
                ),
                name2: Name
              ),
              _
            ) :: _
            if Set("%", "%%").contains(name.decoded) &&
              name2.decoded == "%" &&
              art.tag == StringTag && src.tag == StringTag && ver.tag == StringTag =>
          val depString =
            src.stringValue +
              name.decoded.replace('%', ':') +
              art.stringValue +
              ":" +
              ver.stringValue
          Some((lt.pos, depString))

        case _ =>
          None
      }

    }
  }

  case class SbtLibCompletion(
      pos: Position,
      dependency: String,
  ) extends CompletionPosition {
    override def contribute = {
      val completions =
        CoursierComplete.complete(dependency, includeScala = false)
      val editRange = pos.withStart(pos.start + 1).withEnd(pos.point + 1).toLsp
      completions
        .map(insertText =>
          new TextEditMember(
            filterText = insertText,
            edit = new l.TextEdit(editRange, insertText),
            sym = completionsSymbol(insertText),
            label = Some(insertText)
          )
        )
    }
  }

}
