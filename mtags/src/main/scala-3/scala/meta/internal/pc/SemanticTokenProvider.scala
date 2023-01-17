package scala.meta.internal.pc
import java.{util as ju}

import scala.collection.mutable.ListBuffer
import scala.meta.internal.jdk.CollectionConverters.*

import scala.meta.internal.pc.SemanticTokens.*
import scala.meta.pc.OffsetParams
import scala.meta.tokens.*
import dotty.tools.dotc.ast.tpd.*
import scala.meta.internal.mtags.MtagsEnrichments.*
import scala.annotation.switch

import org.eclipse.lsp4j.SemanticTokenModifiers
import org.eclipse.lsp4j.SemanticTokenTypes
import dotty.tools.dotc.interactive.InteractiveDriver
import dotty.tools.dotc.util.SourceFile
import dotty.tools.dotc.util.SourcePosition
import dotty.tools.dotc.core.Symbols.NoSymbol
import dotty.tools.dotc.core.Symbols.Symbol
import dotty.tools.dotc.core.Contexts.Context
import dotty.tools.dotc.core.Flags
import dotty.tools.dotc.ast.Trees.Annotated
import dotty.tools.dotc.core.Annotations
import dotty.tools.dotc.core.Symbols
import scala.meta.internal.parsers.SoftKeywords

/**
 *  Provides semantic tokens of file(@param params)
 *  according to the LSP specification.
 */
final class SemanticTokenProvider(
    driver: InteractiveDriver,
    params: OffsetParams,
):

  val capableTypes = TokenTypes
  val capableModifiers = TokenModifiers
  // Alias for long notation
  val getTypeId: Map[String, Int] = capableTypes.zipWithIndex.toMap
  val getModifierId: Map[String, Int] = capableModifiers.zipWithIndex.toMap

  implicit val ord: Ordering[NodeInfo] = Ordering.fromLessThan((ni1, ni2) =>
    if ni1.pos.start == ni2.pos.start then ni1.pos.end < ni2.pos.end
    else ni1.pos.start < ni2.pos.start
  )

  // Initialize Tree
  case class NodeInfo(
      sym: Option[Symbol],
      pos: SourcePosition,
  )
  object Collector extends PcCollector[NodeInfo](driver, params):
    override def collect(
        parent: Option[Tree]
    )(tree: Tree, pos: SourcePosition, symbol: Option[Symbol]): NodeInfo =
      val sym = symbol.fold(tree.symbol)(s => s)
      // pprint.log(symbol)
      // pprint.log(sym)

      if sym != NoSymbol && sym != null then NodeInfo(Some(sym), pos)
      else NodeInfo(None, pos)
  given Context = Collector.ctx
  val nodes0: List[NodeInfo] =
    Collector.result(allOccurences = true)
  val nodes = nodes0.filter(_.pos.exists).sorted

  /**
   * Main method.  Fist, Codes are convert to Scala.Meta.Tokens.
   * And a semantic token, which is composed by 5 Ints
   * are provided for each meta-token. If a meta-token is
   * Idenitifier, the attributes (e.g. constant or not)
   * are gotten using presentation Compler.
   * All semantic tokens is flattend to a list and returned.
   */
  def provide(): ju.List[Integer] =

    val buffer = ListBuffer.empty[Integer]

    import scala.meta.*
    var cLine = Line(0, 0) // Current Line
    var lastProvided = SingleLineToken(cLine, 0, None)
    var nodesIterator: List[NodeInfo] = nodes
    implicit val dialect = scala.meta.dialects.Scala3
    for (tk <- params.text().tokenize.get) yield

      val (tokenType, tokenModifier, nodesIterator0) =
        getTypeAndMod(tk, nodesIterator)
      nodesIterator = nodesIterator0
      var cOffset = tk.pos.start // Current Offset
      var providing = SingleLineToken(cLine, cOffset, Some(lastProvided.copy()))

      // If a meta-Token is over multiline,
      // semantic-token is provided by each line.
      // For ecample, Comment or Literal String.
      for wkStr <- tk.text.toCharArray.toList.map(c => c.toString) do
        cOffset += 1
        if wkStr == "\r" then providing.countCR

        // Token Break
        if wkStr == "\n" | cOffset == tk.pos.end then
          providing.endOffset =
            if wkStr == "\n" then cOffset - 1
            else cOffset

          if tokenType != -1 then
            buffer.++=(
              List(
                providing.deltaLine,
                providing.deltaStartChar,
                providing.charSize,
                tokenType,
                tokenModifier,
              )
            )
            lastProvided = providing
          // Line Break
          if wkStr == "\n" then cLine = Line(cLine.number + 1, cOffset)
          providing = SingleLineToken(cLine, cOffset, Some(lastProvided.copy()))
        end if
      end for
      // end for-wkStr
    end for
    // end for-tk

    buffer.toList.asJava
  end provide

  // Dealing with single-line semanticToken
  case class Line(
      val number: Int,
      val startOffset: Int,
  )
  case class SingleLineToken(
      line: Line, // line which token on
      startOffset: Int, // Offset from start of file.
      lastToken: Option[SingleLineToken],
  ):
    var endOffset: Int = 0
    var crCount: Int = 0
    def charSize: Int = endOffset - startOffset - crCount
    def deltaLine: Int =
      line.number - this.lastToken.map(_.line.number).getOrElse(0)

    def deltaStartChar: Int =
      if deltaLine == 0 then
        startOffset - lastToken.map(_.startOffset).getOrElse(0)
      else startOffset - line.startOffset
    def countCR: Unit = crCount += 1
  end SingleLineToken

  /**
   * This function returns -1 when capable Type is nothing.
   *  TokenTypes that can be on multilines are handled in another func.
   *  See Token.Comment in this file.
   */
  private def typeOfNonIdentToken(
      tk: scala.meta.tokens.Token
  ): Integer =
    val SoftKeywordsUnapply = SoftKeywords(scala.meta.dialects.Scala3)
    tk match
      // Alphanumeric keywords
      case _: Token.ModifierKeyword => getTypeId(SemanticTokenTypes.Modifier)
      case _: Token.Keyword =>
        getTypeId(SemanticTokenTypes.Keyword)

      case SoftKeywordsUnapply.KwAs() => getTypeId(SemanticTokenTypes.Keyword)
      case SoftKeywordsUnapply.KwDerives() =>
        getTypeId(SemanticTokenTypes.Keyword)
      case SoftKeywordsUnapply.KwEnd() => getTypeId(SemanticTokenTypes.Keyword)
      case SoftKeywordsUnapply.KwExtension() =>
        getTypeId(SemanticTokenTypes.Keyword)
      case SoftKeywordsUnapply.KwInfix() =>
        getTypeId(SemanticTokenTypes.Keyword)
      case SoftKeywordsUnapply.KwInline() =>
        getTypeId(SemanticTokenTypes.Keyword)
      case SoftKeywordsUnapply.KwOpaque() =>
        getTypeId(SemanticTokenTypes.Keyword)
      case SoftKeywordsUnapply.KwOpen() => getTypeId(SemanticTokenTypes.Keyword)
      case SoftKeywordsUnapply.KwTransparent() =>
        getTypeId(SemanticTokenTypes.Keyword)
      case SoftKeywordsUnapply.KwUsing() =>
        getTypeId(SemanticTokenTypes.Keyword)
      case _: Token.KwNull => getTypeId(SemanticTokenTypes.Keyword)
      case _: Token.KwTrue => getTypeId(SemanticTokenTypes.Keyword)
      case _: Token.KwFalse => getTypeId(SemanticTokenTypes.Keyword)

      // extends Symbolic keywords
      case _: Token.Hash => getTypeId(SemanticTokenTypes.Keyword)
      case _: Token.Viewbound => getTypeId(SemanticTokenTypes.Operator)
      case _: Token.LeftArrow => getTypeId(SemanticTokenTypes.Operator)
      case _: Token.Subtype => getTypeId(SemanticTokenTypes.Keyword)
      case _: Token.RightArrow => getTypeId(SemanticTokenTypes.Operator)
      case _: Token.Supertype => getTypeId(SemanticTokenTypes.Keyword)
      case _: Token.At => getTypeId(SemanticTokenTypes.Keyword)
      case _: Token.Underscore => getTypeId(SemanticTokenTypes.Variable)
      case _: Token.TypeLambdaArrow => getTypeId(SemanticTokenTypes.Operator)
      case _: Token.ContextArrow => getTypeId(SemanticTokenTypes.Operator)

      // Constant
      case _: Token.Constant.Int | _: Token.Constant.Long |
          _: Token.Constant.Float | _: Token.Constant.Double =>
        getTypeId(SemanticTokenTypes.Number)
      case _: Token.Constant.String | _: Token.Constant.Char =>
        getTypeId(SemanticTokenTypes.String)
      case _: Token.Constant.Symbol => getTypeId(SemanticTokenTypes.Property)

      // Comment
      case _: Token.Comment => getTypeId(SemanticTokenTypes.Comment)

      // Interpolation
      case _: Token.Interpolation.Id | _: Token.Interpolation.SpliceStart =>
        getTypeId(SemanticTokenTypes.Keyword)
      case _: Token.Interpolation.Start | _: Token.Interpolation.Part |
          _: Token.Interpolation.SpliceEnd | _: Token.Interpolation.End =>
        getTypeId(SemanticTokenTypes.String) // $ symbol

      // Default
      case _ => -1
    end match
  end typeOfNonIdentToken

  /**
   * The position of @param tk must be incremented from the previous call.
   */
  def pickFromTraversed(
      tk: scala.meta.tokens.Token,
      nodesIterator: List[NodeInfo],
  )(using Context): Option[(NodeInfo, List[NodeInfo])] =

    val adjustForBacktick: Int =
      var ret: Int = 0
      val cName = tk.text.toCharArray()
      if cName.size >= 2 then
        if cName(0) == '`'
          && cName(cName.size - 1) == '`'
        then ret = 2
      ret
    def isTarget(node: NodeInfo): Boolean =
      node.pos.start == tk.pos.start &&
        node.pos.end + adjustForBacktick == tk.pos.end
    val candidates = nodesIterator.dropWhile(_.pos.start < tk.pos.start)
    candidates
      .takeWhile(_.pos.start == tk.pos.start)
      .filter(isTarget) match
      case Nil => None
      case node :: Nil => Some((node, candidates))
      case manyNodes =>
        val preFilter = manyNodes.collect {
          case ni @ NodeInfo(Some(sym), p)
              if Collector
                .symbolAlternatives(sym)
                .exists(_.decodedName == tk.text) =>
            (ni, candidates)
        }
        preFilter.collectFirst {
          case (ni @ NodeInfo(Some(sym), _), candidates)
              if !sym.is(Flags.Synthetic) =>
            (ni, candidates)
        }
    end match
  end pickFromTraversed

  /**
   * returns (SemanticTokenType, SemanticTokenModifier) of @param tk
   */
  private def getTypeAndMod(
      tk: scala.meta.tokens.Token,
      nodesIterator: List[NodeInfo],
  ): (Int, Int, List[NodeInfo]) =
    tk match
      case ident: Token.Ident =>
        IdentTypeAndMod(ident, nodesIterator) match
          case (-1, 0, _) => (typeOfNonIdentToken(tk), 0, nodesIterator)
          case res => res

      case _ => (typeOfNonIdentToken(tk), 0, nodesIterator)

  /**
   * returns (SemanticTokenType, SemanticTokenModifier) of @param tk
   */
  private def IdentTypeAndMod(
      ident: Token.Ident,
      nodesIterator: List[NodeInfo],
  ): (Int, Int, List[NodeInfo]) =
    val default = (-1, 0, nodesIterator)

    val isOperatorName = (ident.name.last: @switch) match
      case '~' | '!' | '@' | '#' | '%' | '^' | '*' | '+' | '-' | '<' | '>' |
          '?' | ':' | '=' | '&' | '|' | '/' | '\\' =>
        true
      case _ => false
    val ret =
      for (
        (nodeInfo, nodesIterator) <- pickFromTraversed(ident, nodesIterator);
        sym <- nodeInfo.sym
      ) yield
        var mod: Int = 0
        def addPwrToMod(tokenID: String) =
          val place: Int = getModifierId(tokenID)
          if place != -1 then mod += (1 << place)
        // get Type
        val typ =
          if sym.is(
              Flags.Param
            ) && !sym.isTypeParam && !sym.owner.isClassConstructor
          then getTypeId(SemanticTokenTypes.Parameter)
          else if sym.isTypeParam || sym.isSkolem then
            getTypeId(SemanticTokenTypes.TypeParameter)
          else if isOperatorName then getTypeId(SemanticTokenTypes.Operator)
          // Java Enum
          else if // enumy trzeba poprawic
            sym.is(Flags.Enum) || sym.isAllOf(Flags.EnumVal)
          then getTypeId(SemanticTokenTypes.Enum)
          // else if (sym.hasFlag(scala.reflect.internal.ModifierFlags.JAVA_ENUM))
          // getTypeId(SemanticTokenTypes.EnumMember)
          // See symbol.keystring about following conditions.
          else if sym.is(Flags.Trait) then
            getTypeId(SemanticTokenTypes.Interface) // "interface"
          // else if (sym.isTrait)
          // getTypeId(SemanticTokenTypes.Interface) // "trait"
          else if sym.isClass then
            getTypeId(SemanticTokenTypes.Class) // "class"
          else if sym.isType && !sym.is(Flags.Param) then
            getTypeId(SemanticTokenTypes.Type) // "type"
          else if sym.is(Flags.Mutable) then
            getTypeId(SemanticTokenTypes.Variable) // "var"
          else if sym.is(Flags.Package) then
            getTypeId(SemanticTokenTypes.Namespace) // "package"
          else if sym.is(Flags.Module) then
            getTypeId(SemanticTokenTypes.Class) // "object"
          else if sym.is(Flags.Method) then
            if sym.isGetter | sym.isSetter then
              getTypeId(SemanticTokenTypes.Variable)
            else getTypeId(SemanticTokenTypes.Method) // "def"
          else if sym.isTerm &&
            (!sym.is(Flags.Param) || sym.is(
              Flags.ParamAccessor
            ) || sym.owner.isClassConstructor)
          then
            addPwrToMod(SemanticTokenModifiers.Readonly)
            getTypeId(SemanticTokenTypes.Variable) // "val"
          else -1

        // Modifiers except by ReadOnly
        if sym.is(Flags.Abstract) then
          addPwrToMod(SemanticTokenModifiers.Abstract)
        if sym.annotations.exists(
            _.show.startsWith("@deprecated")
          ) && sym.decodedName != "deprecated"
        then addPwrToMod(SemanticTokenModifiers.Deprecated)
        // if (sym.owner.is(Flags.Module)) addPwrToMod(SemanticTokenModifiers.Static)

        (typ, mod, nodesIterator)

    ret.getOrElse(default)
  end IdentTypeAndMod
end SemanticTokenProvider
