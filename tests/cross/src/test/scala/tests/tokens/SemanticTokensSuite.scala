package tests.tokens

import java.net.URI

import scala.meta.internal.jdk.CollectionConverters._

import munit.Location
import munit.TestOptions
import tests.BasePCSuite
import tests.TestSemanticTokens
import scala.meta.internal.metals.CompilerOffsetParams

class SemanticTokensSuite extends BasePCSuite {

  check(
    "class, object, var, val(readonly), method, type, parameter, String(single-line)",
    s"""|<<class>>/*keyword*/  <<Test>>/*class*/{
        |
        | <<var>>/*keyword*/ <<wkStr>>/*variable*/ = <<"Dog-">>/*string*/
        | <<val>>/*keyword*/ <<nameStr>>/*variable,readonly*/ = <<"Jack">>/*string*/
        |
        | <<def>>/*keyword*/ <<Main>>/*method*/={
        |
        |  <<val>>/*keyword*/ <<preStr>>/*variable,readonly*/= <<"I am ">>/*string*/
        |  <<var>>/*keyword*/ <<postStr>>/*variable*/= <<"in a house. ">>/*string*/
        |  <<wkStr>>/*variable*/=<<nameStr>>/*variable,readonly*/ <<+>>/*method*/ <<"Cat-">>/*string*/
        |
        |  <<testC>>/*class*/.<<bc>>/*method*/(<<preStr>>/*variable,readonly*/
        |    <<+>>/*method*/ <<wkStr>>/*variable*/
        |    <<+>>/*method*/ <<preStr>>/*variable,readonly*/)
        | }
        |}
        |
        |<<object>>/*keyword*/  <<testC>>/*class*/{
        |
        | <<def>>/*keyword*/ <<bc>>/*method*/(<<msg>>/*parameter*/:<<String>>/*type*/)={
        |   <<println>>/*method*/(<<msg>>/*parameter*/)
        | }
        |}
        |""".stripMargin,
  )

  check(
    "Comment(Single-Line, Multi-Line)",
    s"""|
        |<<object>>/*keyword*/ <<Main>>/*class*/{
        |
        |   <</**>>/*comment*/
        |<<   * Test of Comment Block>>/*comment*/
        |<<   */>>/*comment*/  <<val>>/*keyword*/ <<x>>/*variable,readonly*/ = <<1>>/*number*/
        |
        |  <<def>>/*keyword*/ <<add>>/*method*/(<<a>>/*parameter*/ : <<Int>>/*class,abstract*/) = {
        |    <<// Single Line Comment>>/*comment*/
        |    <<a>>/*parameter*/ <<+>>/*method,abstract*/ <<1>>/*number*/ <<// com = 1>>/*comment*/
        |   }
        |}
        |
        |
        |""".stripMargin,
    compat = Map(
      "3" ->
        s"""|
            |<<object>>/*keyword*/ <<Main>>/*class*/{
            |
            |   <</**>>/*comment*/
            |<<   * Test of Comment Block>>/*comment*/
            |<<   */>>/*comment*/  <<val>>/*keyword*/ <<x>>/*variable,readonly*/ = <<1>>/*number*/
            |
            |  <<def>>/*keyword*/ <<add>>/*method*/(<<a>>/*parameter*/ : <<Int>>/*class,abstract*/) = {
            |    <<// Single Line Comment>>/*comment*/
            |    <<a>>/*parameter*/ <<+>>/*method*/ <<1>>/*number*/ <<// com = 1>>/*comment*/
            |   }
            |}
            |
            |
            |""".stripMargin
    ),
  )

  check(
    "number literal, Static",
    s"""|
        |<<object>>/*keyword*/ <<ab>>/*class*/ {
        |  <<var>>/*keyword*/  <<iVar>>/*variable*/:<<Int>>/*class,abstract*/ = <<1>>/*number*/
        |  <<val>>/*keyword*/  <<iVal>>/*variable,readonly*/:<<Double>>/*class,abstract*/ = <<4.94065645841246544e-324d>>/*number*/
        |  <<val>>/*keyword*/  <<fVal>>/*variable,readonly*/:<<Float>>/*class,abstract*/ = <<1.40129846432481707e-45>>/*number*/
        |  <<val>>/*keyword*/  <<lVal>>/*variable,readonly*/:<<Long>>/*class,abstract*/ = <<9223372036854775807L>>/*number*/
        |}
        |
        |<<object>>/*keyword*/ <<sample10>>/*class*/ {
        |  <<def>>/*keyword*/ <<main>>/*method*/(<<args>>/*parameter*/: <<Array>>/*class*/[<<String>>/*type*/]) ={
        |    <<println>>/*method*/(
        |     (<<ab>>/*class*/.<<iVar>>/*variable*/ <<+>>/*method,abstract*/ <<ab>>/*class*/.<<iVal>>/*variable,readonly*/).<<toString>>/*method*/
        |    )
        |  }
        |}
        |""".stripMargin,
    compat = Map(
      "3" ->
        s"""|
            |<<object>>/*keyword*/ <<ab>>/*class*/ {
            |  <<var>>/*keyword*/  <<iVar>>/*variable*/:<<Int>>/*class,abstract*/ = <<1>>/*number*/
            |  <<val>>/*keyword*/  <<iVal>>/*variable,readonly*/:<<Double>>/*class,abstract*/ = <<4.94065645841246544e-324d>>/*number*/
            |  <<val>>/*keyword*/  <<fVal>>/*variable,readonly*/:<<Float>>/*class,abstract*/ = <<1.40129846432481707e-45>>/*number*/
            |  <<val>>/*keyword*/  <<lVal>>/*variable,readonly*/:<<Long>>/*class,abstract*/ = <<9223372036854775807L>>/*number*/
            |}
            |
            |<<object>>/*keyword*/ <<sample10>>/*class*/ {
            |  <<def>>/*keyword*/ <<main>>/*method*/(<<args>>/*parameter*/: <<Array>>/*class*/[<<String>>/*type*/]) ={
            |    <<println>>/*method*/(
            |     (<<ab>>/*class*/.<<iVar>>/*variable*/ <<+>>/*method*/ <<ab>>/*class*/.<<iVal>>/*variable,readonly*/).<<toString>>/*method*/
            |    )
            |  }
            |}
            |""".stripMargin
    ),
  )

  check(
    "abstract(modifier), trait, type parameter",
    s"""|
        |<<package>>/*keyword*/ <<a>>/*namespace*/.<<b>>/*namespace*/
        |<<object>>/*keyword*/ <<Sample5>>/*class*/ {
        |
        |  <<def>>/*keyword*/ <<main>>/*method*/(<<args>>/*parameter*/: <<Array>>/*class*/[<<String>>/*type*/]) ={
        |      <<val>>/*keyword*/ <<itr>>/*variable,readonly*/ = <<new>>/*keyword*/ <<IntIterator>>/*class*/(<<5>>/*number*/)
        |      <<var>>/*keyword*/ <<str>>/*variable*/ = <<itr>>/*variable,readonly*/.<<next>>/*method*/().<<toString>>/*method*/ <<+>>/*method*/ <<",">>/*string*/
        |          <<str>>/*variable*/ += <<itr>>/*variable,readonly*/.<<next>>/*method*/().<<toString>>/*method*/
        |      <<println>>/*method*/(<<"count:">>/*string*/<<+>>/*method*/<<str>>/*variable*/)
        |  }
        |
        |  <<trait>>/*keyword*/ <<Iterator>>/*interface,abstract*/[<<A>>/*typeParameter,abstract*/] {
        |    <<def>>/*keyword*/ <<next>>/*method,abstract*/(): <<A>>/*typeParameter,abstract*/
        |  }
        |
        |  <<abstract>>/*modifier*/ <<class>>/*keyword*/ <<hasLogger>>/*class,abstract*/ {
        |    <<def>>/*keyword*/ <<log>>/*method*/(<<str>>/*parameter*/:<<String>>/*type*/) = {<<println>>/*method*/(<<str>>/*parameter*/)}
        |  }
        |
        |  <<class>>/*keyword*/ <<IntIterator>>/*class*/(<<to>>/*variable,readonly*/: <<Int>>/*class,abstract*/)
        |  <<extends>>/*keyword*/ <<hasLogger>>/*class,abstract*/ <<with>>/*keyword*/ <<Iterator>>/*interface,abstract*/[<<Int>>/*class,abstract*/]  {
        |    <<private>>/*modifier*/ <<var>>/*keyword*/ <<current>>/*variable*/ = <<0>>/*number*/
        |    <<override>>/*modifier*/ <<def>>/*keyword*/ <<next>>/*method*/(): <<Int>>/*class,abstract*/ = {
        |      <<if>>/*keyword*/ (<<current>>/*variable*/ <<<>>/*method,abstract*/ <<to>>/*variable,readonly*/) {
        |        <<log>>/*method*/(<<"main">>/*string*/)
        |        <<val>>/*keyword*/ <<t>>/*variable,readonly*/ = <<current>>/*variable*/
        |        <<current>>/*variable*/ = <<current>>/*variable*/ <<+>>/*method,abstract*/ <<1>>/*number*/
        |        <<t>>/*variable,readonly*/
        |      } <<else>>/*keyword*/ <<0>>/*number*/
        |    }
        |  }
        |}
        |
        |
        |""".stripMargin,
    compat = Map(
      "3" ->
        s"""|
            |<<package>>/*keyword*/ <<a>>/*namespace*/.<<b>>/*namespace*/
            |<<object>>/*keyword*/ <<Sample5>>/*class*/ {
            |
            |  <<def>>/*keyword*/ <<main>>/*method*/(<<args>>/*parameter*/: <<Array>>/*class*/[<<String>>/*type*/]) ={
            |      <<val>>/*keyword*/ <<itr>>/*variable,readonly*/ = <<new>>/*keyword*/ <<IntIterator>>/*class*/(<<5>>/*number*/)
            |      <<var>>/*keyword*/ <<str>>/*variable*/ = <<itr>>/*variable,readonly*/.<<next>>/*method*/().<<toString>>/*method*/ <<+>>/*method*/ <<",">>/*string*/
            |          <<str>>/*variable*/ += <<itr>>/*variable,readonly*/.<<next>>/*method*/().<<toString>>/*method*/
            |      <<println>>/*method*/(<<"count:">>/*string*/<<+>>/*method*/<<str>>/*variable*/)
            |  }
            |
            |  <<trait>>/*keyword*/ <<Iterator>>/*interface*/[<<A>>/*typeParameter*/] {
            |    <<def>>/*keyword*/ <<next>>/*method*/(): <<A>>/*typeParameter*/
            |  }
            |
            |  <<abstract>>/*modifier*/ <<class>>/*keyword*/ <<hasLogger>>/*class,abstract*/ {
            |    <<def>>/*keyword*/ <<log>>/*method*/(<<str>>/*parameter*/:<<String>>/*type*/) = {<<println>>/*method*/(<<str>>/*parameter*/)}
            |  }
            |
            |  <<class>>/*keyword*/ <<IntIterator>>/*class*/(<<to>>/*variable,readonly*/: <<Int>>/*class,abstract*/)
            |  <<extends>>/*keyword*/ <<hasLogger>>/*class,abstract*/ <<with>>/*keyword*/ <<Iterator>>/*interface*/[<<Int>>/*class,abstract*/]  {
            |    <<private>>/*modifier*/ <<var>>/*keyword*/ <<current>>/*variable*/ = <<0>>/*number*/
            |    <<override>>/*modifier*/ <<def>>/*keyword*/ <<next>>/*method*/(): <<Int>>/*class,abstract*/ = {
            |      <<if>>/*keyword*/ (<<current>>/*variable*/ <<<>>/*method*/ <<to>>/*variable,readonly*/) {
            |        <<log>>/*method*/(<<"main">>/*string*/)
            |        <<val>>/*keyword*/ <<t>>/*variable,readonly*/ = <<current>>/*variable*/
            |        <<current>>/*variable*/ = <<current>>/*variable*/ <<+>>/*method*/ <<1>>/*number*/
            |        <<t>>/*variable,readonly*/
            |      } <<else>>/*keyword*/ <<0>>/*number*/
            |    }
            |  }
            |}
            |
            |
            |""".stripMargin
    ),
  )

  check(
    "deprecated",
    s"""|<<object>>/*keyword*/ <<sample9>>/*class*/ {
        |  <<@>>/*keyword*/<<deprecated>>/*class*/(<<"this method will be removed">>/*string*/, <<"FooLib 12.0">>/*string*/)
        |  <<def>>/*keyword*/ <<oldMethod>>/*method,deprecated*/(<<x>>/*parameter*/: <<Int>>/*class,abstract*/) = <<x>>/*parameter*/
        |
        |  <<def>>/*keyword*/ <<main>>/*method*/(<<args>>/*parameter*/: <<Array>>/*class*/[<<String>>/*type*/]) ={
        |    <<val>>/*keyword*/ <<str>>/*variable,readonly*/ = <<oldMethod>>/*method,deprecated*/(<<2>>/*number*/).<<toString>>/*method*/
        |     <<println>>/*method*/(<<"Hello, world!">>/*string*/<<+>>/*method*/ <<str>>/*variable,readonly*/)
        |  }
        |}
        |""".stripMargin,
  )

  check(
    "import(Out of File)",
    s"""|
        |<<import>>/*keyword*/ <<scala>>/*namespace*/.<<math>>/*namespace*/.<<sqrt>>/*method*/
        |<<object>>/*keyword*/ <<sample3>>/*class*/ {
        |
        |  <<def>>/*keyword*/ <<sqrtplus1>>/*method*/(<<x>>/*parameter*/: <<Int>>/*class,abstract*/)
        |     = <<sqrt>>/*method*/(<<x>>/*parameter*/).<<toString>>/*method*/()
        |
        |  <<def>>/*keyword*/ <<main>>/*method*/(<<args>>/*parameter*/: <<Array>>/*class*/[<<String>>/*type*/]) ={
        |    <<println>>/*method*/(<<"Hello, world! : ">>/*string*/ <<+>>/*method*/ <<sqrtplus1>>/*method*/(<<2>>/*number*/))
        |  }
        |}
        |
        |""".stripMargin,
  )

  check(
    "anonymous-class",
    s"""|<<object>>/*keyword*/ <<A>>/*class*/ {
        |  <<trait>>/*keyword*/ <<Methodable>>/*interface,abstract*/[<<T>>/*typeParameter,abstract*/] {
        |    <<def>>/*keyword*/ <<method>>/*method,abstract*/(<<asf>>/*parameter*/: <<T>>/*typeParameter,abstract*/): <<Int>>/*class,abstract*/
        |  }
        |
        |  <<abstract>>/*modifier*/ <<class>>/*keyword*/ <<Alphabet>>/*class,abstract*/(<<alp>>/*variable,readonly*/: <<Int>>/*class,abstract*/) <<extends>>/*keyword*/ <<Methodable>>/*interface,abstract*/[<<String>>/*type*/] {
        |    <<def>>/*keyword*/ <<method>>/*method*/(<<adf>>/*parameter*/: <<String>>/*type*/) = <<123>>/*number*/
        |  }
        |  <<val>>/*keyword*/ <<a>>/*variable,readonly*/ = <<new>>/*keyword*/ <<Alphabet>>/*class,abstract*/(<<alp>>/*parameter*/ = <<10>>/*number*/) {
        |    <<override>>/*modifier*/ <<def>>/*keyword*/ <<method>>/*method*/(<<adf>>/*parameter*/: <<String>>/*type*/): <<Int>>/*class,abstract*/ = <<321>>/*number*/
        |  }
        |}""".stripMargin,
  )
  check(
    "extension".tag(IgnoreScala2),
    s"""|<<package>>/*keyword*/ <<example>>/*namespace*/
        |
        |<<extension>>/*keyword*/ (<<i>>/*parameter*/: <<Int>>/*class,abstract*/)
        |  <<def>>/*keyword*/ <<asString>>/*method*/: <<String>>/*type*/ = <<i>>/*parameter*/.<<toString>>/*method*/
        |
        |<<extension>>/*keyword*/ (<<s>>/*parameter*/: <<String>>/*type*/) {
        |  <<def>>/*keyword*/ <<asInt>>/*method*/: <<Int>>/*class,abstract*/ = <<s>>/*parameter*/.<<toInt>>/*method*/
        |  <<def>>/*keyword*/ <<double>>/*method*/: <<String>>/*type*/ = <<s>>/*parameter*/ <<*>>/*method*/ <<2>>/*number*/
        |}
        |
        |<<trait>>/*keyword*/ <<AbstractExtension>>/*interface*/ {
        |  <<extension>>/*keyword*/ (<<d>>/*parameter*/: <<Double>>/*class,abstract*/) {
        |    <<def>>/*keyword*/ <<abc>>/*method*/: <<String>>/*type*/
        |  }
        |}
        |""".stripMargin,
  )

  check(
    "abstract-given".tag(IgnoreScala2),
    s"""|<<package>>/*keyword*/ <<example>>/*namespace*/
        |
        |<<abstract>>/*modifier*/ <<class>>/*keyword*/ <<AbstractGiven>>/*class,abstract*/:
        |  <<given>>/*keyword*/ <<int>>/*method*/: <<Int>>/*class,abstract*/
        |
        |""".stripMargin,
  )

  check(
    "enum1".tag(IgnoreScala2),
    s"""|<<package>>/*keyword*/ <<example>>/*namespace*/
        |
        |<<enum>>/*keyword*/ <<Color>>/*enum,abstract*/(<<val>>/*keyword*/ <<rgb>>/*variable,readonly*/: <<Int>>/*class,abstract*/):
        |   <<case>>/*keyword*/ <<Red>>/*enum*/   <<extends>>/*keyword*/ <<Color>>/*enum,abstract*/(<<0xFF0000>>/*number*/)
        |   <<case>>/*keyword*/ <<Green>>/*enum*/ <<extends>>/*keyword*/ <<Color>>/*enum,abstract*/(<<0x00FF00>>/*number*/)
        |   <<case>>/*keyword*/ <<Blue>>/*enum*/  <<extends>>/*keyword*/ <<Color>>/*enum,abstract*/(<<0x0000FF>>/*number*/)
        |
        |""".stripMargin,
  )

  check(
    "enum2".tag(IgnoreScala2),
    s"""|<<package>>/*keyword*/ <<example>>/*namespace*/
        |
        |<<enum>>/*keyword*/ <<FooEnum>>/*enum,abstract*/:
        |  <<case>>/*keyword*/ <<Bar>>/*enum*/, <<Baz>>/*enum*/
        |<<object>>/*keyword*/ <<FooEnum>>/*class*/
        |""".stripMargin,
  )

  check(
    "toplevel-val".tag(IgnoreScala2),
    s"""|<<package>>/*keyword*/ <<example>>/*namespace*/
        |
        |<<def>>/*keyword*/ <<foo>>/*method*/(): <<Int>>/*class,abstract*/ = <<42>>/*number*/
        |
        |<<val>>/*keyword*/ <<abc>>/*variable,readonly*/: <<String>>/*type*/ = <<"sds">>/*string*/
        |
        |<<// tests jar's indexing on Windows>>/*comment*/
        |<<type>>/*keyword*/ <<SourceToplevelTypeFromDepsRef>>/*type*/ = <<EmptyTuple>>/*type*/
        |""".stripMargin,
  )

  check(
    "given-ord".tag(IgnoreScala2),
    s"""|<<package>>/*keyword*/ <<example>>/*namespace*/
        |<<trait>>/*keyword*/ <<Ord>>/*interface*/[<<T>>/*typeParameter*/]:
        |   <<def>>/*keyword*/ <<compare>>/*method*/(<<x>>/*parameter*/: <<T>>/*typeParameter*/, <<y>>/*parameter*/: <<T>>/*typeParameter*/): <<Int>>/*class,abstract*/
        |<<given>>/*keyword*/ <<intOrd>>/*class*/: <<Ord>>/*interface*/[<<Int>>/*class,abstract*/] <<with>>/*keyword*/
        |   <<def>>/*keyword*/ <<compare>>/*method*/(<<x>>/*parameter*/: <<Int>>/*class,abstract*/, <<y>>/*parameter*/: <<Int>>/*class,abstract*/) =
        |     <<if>>/*keyword*/ <<x>>/*parameter*/ <<<>>/*method*/ <<y>>/*parameter*/ <<then>>/*keyword*/ -<<1>>/*number*/ <<else>>/*keyword*/ <<if>>/*keyword*/ <<x>>/*parameter*/ <<>>>/*method*/ <<y>>/*parameter*/ <<then>>/*keyword*/ +<<1>>/*number*/ <<else>>/*keyword*/ <<0>>/*number*/
        |<<given>>/*keyword*/ <<Ord>>/*interface*/[<<String>>/*type*/] <<with>>/*keyword*/
        |   <<def>>/*keyword*/ <<compare>>/*method*/(<<x>>/*parameter*/: <<String>>/*type*/, <<y>>/*parameter*/: <<String>>/*type*/) =
        |     <<x>>/*parameter*/.<<compare>>/*method*/(<<y>>/*parameter*/)
        |""".stripMargin,
  )

  check(
    "and-or-type".tag(IgnoreScala2),
    s"""|<<package>>/*keyword*/ <<example>>/*namespace*/
        |
        |<<trait>>/*keyword*/ <<Cancelable>>/*interface*/ 
        |<<trait>>/*keyword*/ <<Movable>>/*interface*/ 
        |
        |<<type>>/*keyword*/ <<Y>>/*type*/ = (<<Cancelable>>/*interface*/ <<&>>/*type*/ <<Movable>>/*interface*/)
        |
        |<<type>>/*keyword*/ <<X>>/*type*/ = <<String>>/*type*/ <<|>>/*type*/ <<Int>>/*class,abstract*/
        |""".stripMargin,
  )

  check(
    "given-big".tag(IgnoreScala2),
    s"""|<<package>>/*keyword*/ <<example>>/*namespace*/
        |
        |<<given>>/*keyword*/ <<intValue>>/*variable,readonly*/: <<Int>>/*class,abstract*/ = <<4>>/*number*/
        |<<given>>/*keyword*/ <<String>>/*type*/ = <<"str">>/*string*/
        |<<given>>/*keyword*/ (<<using>>/*keyword*/ <<i>>/*parameter*/: <<Int>>/*class,abstract*/): <<Double>>/*class,abstract*/ = <<4.0>>/*number*/
        |<<given>>/*keyword*/ [<<T>>/*typeParameter*/]: <<List>>/*type*/[<<T>>/*typeParameter*/] = <<Nil>>/*variable*/
        |<<given>>/*keyword*/ <<given_Char>>/*variable,readonly*/: <<Char>>/*class,abstract*/ = <<'?'>>/*string*/
        |<<given>>/*keyword*/ `given_Float`: <<Float>>/*class,abstract*/ = <<3.0>>/*number*/
        |<<given>>/*keyword*/ `* *`: <<Long>>/*class,abstract*/ = <<5>>/*number*/
        |
        |<<def>>/*keyword*/ <<method>>/*method*/(<<using>>/*keyword*/ <<Int>>/*class,abstract*/) = <<"">>/*string*/
        |
        |<<object>>/*keyword*/ <<X>>/*class*/ {
        |  <<given>>/*keyword*/ <<Double>>/*class,abstract*/ = <<4.0>>/*number*/
        |  <<val>>/*keyword*/ <<double>>/*variable,readonly*/ = <<given_Double>>/*variable,readonly*/
        |
        |  <<given>>/*keyword*/ <<of>>/*method*/[<<A>>/*typeParameter*/]: <<Option>>/*class,abstract*/[<<A>>/*typeParameter*/] = <<???>>/*method*/
        |}
        |
        |<<trait>>/*keyword*/ <<Xg>>/*interface*/:
        |  <<def>>/*keyword*/ <<doX>>/*method*/: <<Int>>/*class,abstract*/
        |
        |<<trait>>/*keyword*/ <<Yg>>/*interface*/:
        |  <<def>>/*keyword*/ <<doY>>/*method*/: <<String>>/*type*/
        |
        |<<trait>>/*keyword*/ <<Zg>>/*interface*/[<<T>>/*typeParameter*/]:
        |  <<def>>/*keyword*/ <<doZ>>/*method*/: <<List>>/*type*/[<<T>>/*typeParameter*/]
        |
        |<<given>>/*keyword*/ <<Xg>>/*interface*/ <<with>>/*keyword*/
        |  <<def>>/*keyword*/ <<doX>>/*method*/ = <<7>>/*number*/
        |
        |<<given>>/*keyword*/ (<<using>>/*keyword*/ <<Xg>>/*interface*/): <<Yg>>/*interface*/ <<with>>/*keyword*/
        |  <<def>>/*keyword*/ <<doY>>/*method*/ = <<"7">>/*string*/
        |
        |<<given>>/*keyword*/ [<<T>>/*typeParameter*/]: <<Zg>>/*interface*/[<<T>>/*typeParameter*/] <<with>>/*keyword*/
        |  <<def>>/*keyword*/ <<doZ>>/*method*/: <<List>>/*type*/[<<T>>/*typeParameter*/] = <<Nil>>/*variable*/
        |
        |<<val>>/*keyword*/ <<a>>/*variable,readonly*/ = <<intValue>>/*variable,readonly*/
        |<<val>>/*keyword*/ <<b>>/*variable,readonly*/ = <<given_String>>/*variable,readonly*/
        |<<val>>/*keyword*/ <<c>>/*variable,readonly*/ = <<X>>/*class*/.<<given_Double>>/*variable,readonly*/
        |<<val>>/*keyword*/ <<d>>/*variable,readonly*/ = <<given_List_T>>/*method*/[<<Int>>/*class,abstract*/]
        |<<val>>/*keyword*/ <<e>>/*variable,readonly*/ = <<given_Char>>/*variable,readonly*/
        |<<val>>/*keyword*/ <<f>>/*variable,readonly*/ = <<given_Float>>/*variable,readonly*/
        |<<val>>/*keyword*/ <<g>>/*variable,readonly*/ = `* *`
        |<<val>>/*keyword*/ <<i>>/*variable,readonly*/ = <<X>>/*class*/.<<of>>/*method*/[<<Int>>/*class,abstract*/]
        |<<val>>/*keyword*/ <<x>>/*variable,readonly*/ = <<given_Xg>>/*class*/
        |<<val>>/*keyword*/ <<y>>/*variable,readonly*/ = <<given_Yg>>/*method*/
        |<<val>>/*keyword*/ <<z>>/*variable,readonly*/ = <<given_Zg_T>>/*method*/[<<String>>/*type*/]
        |
        |""".stripMargin,
  )

  def check(
      name: TestOptions,
      expected: String,
      compat: Map[String, String] = Map.empty,
  )(implicit location: Location): Unit =
    test(name) {
      val base =
        expected
          .replaceAll(raw"/\*[\w,]+\*/", "")
          .replaceAll(raw"\<\<|\>\>", "")
      val tokens = presentationCompiler
        .semanticTokens(
          CompilerOffsetParams(URI.create("file:/Tokens.scala"), base, 0)
        )
        .get()

      val obtained = TestSemanticTokens.semanticString(
        base,
        tokens.asScala.toList.map(_.toInt),
      )
      assertEquals(
        obtained,
        getExpected(expected, compat, scalaVersion),
      )

    }
}
