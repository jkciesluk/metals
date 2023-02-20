package tests.tokens

import tests.BaseSemanticTokensSuite

class SemanticTokensSuite extends BaseSemanticTokensSuite {

  check(
    "class, object, var, val(readonly), method, type, parameter, String(single-line)",
    s"""|<<class>>/*keyword*/  <<Test>>/*class*/{
        |
        | <<var>>/*keyword*/ <<wkStr>>/*variable*/ <<=>>/*operator*/ <<"Dog-">>/*string*/
        | <<val>>/*keyword*/ <<nameStr>>/*variable,readonly*/ <<=>>/*operator*/ <<"Jack">>/*string*/
        |
        | <<def>>/*keyword*/ <<Main>>/*method*/<<=>>/*operator*/{
        |
        |  <<val>>/*keyword*/ <<preStr>>/*variable,readonly*/<<=>>/*operator*/ <<"I am ">>/*string*/
        |  <<var>>/*keyword*/ <<postStr>>/*variable*/<<=>>/*operator*/ <<"in a house. ">>/*string*/
        |  <<wkStr>>/*variable*/<<=>>/*operator*/<<nameStr>>/*variable,readonly*/  <<+>>/*operator*/ <<"Cat-">>/*string*/
        |
        |  <<testC>>/*class*/.<<bc>>/*method*/(<<preStr>>/*variable,readonly*/
        |     <<+>>/*operator*/ <<wkStr>>/*variable*/
        |     <<+>>/*operator*/ <<preStr>>/*variable,readonly*/)
        | }
        |}
        |
        |<<object>>/*keyword*/  <<testC>>/*class*/{
        |
        | <<def>>/*keyword*/ <<bc>>/*method*/(<<msg>>/*parameter*/<<:>>/*operator*/<<String>>/*type*/)<<=>>/*operator*/{
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
        |<<   */>>/*comment*/  <<val>>/*keyword*/ <<x>>/*variable,readonly*/ <<=>>/*operator*/ <<1>>/*number*/
        |
        |  <<def>>/*keyword*/ <<add>>/*method*/(<<a>>/*parameter*/ <<:>>/*operator*/ <<Int>>/*class,abstract*/) <<=>>/*operator*/ {
        |    <<// Single Line Comment>>/*comment*/
        |    <<a>>/*parameter*/  <<+>>/*operator,abstract*/ <<1>>/*number*/ <<// com = 1>>/*comment*/
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
            |<<   */>>/*comment*/  <<val>>/*keyword*/ <<x>>/*variable,readonly*/ <<=>>/*operator*/ <<1>>/*number*/
            |
            |  <<def>>/*keyword*/ <<add>>/*method*/(<<a>>/*parameter*/ <<:>>/*operator*/ <<Int>>/*class,abstract*/) <<=>>/*operator*/ {
            |    <<// Single Line Comment>>/*comment*/
            |    <<a>>/*parameter*/  <<+>>/*operator*/ <<1>>/*number*/ <<// com = 1>>/*comment*/
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
        |  <<var>>/*keyword*/  <<iVar>>/*variable*/<<:>>/*operator*/<<Int>>/*class,abstract*/ <<=>>/*operator*/ <<1>>/*number*/
        |  <<val>>/*keyword*/  <<iVal>>/*variable,readonly*/<<:>>/*operator*/<<Double>>/*class,abstract*/ <<=>>/*operator*/ <<4.94065645841246544e-324d>>/*number*/
        |  <<val>>/*keyword*/  <<fVal>>/*variable,readonly*/<<:>>/*operator*/<<Float>>/*class,abstract*/ <<=>>/*operator*/ <<1.40129846432481707e-45>>/*number*/
        |  <<val>>/*keyword*/  <<lVal>>/*variable,readonly*/<<:>>/*operator*/<<Long>>/*class,abstract*/ <<=>>/*operator*/ <<9223372036854775807L>>/*number*/
        |}
        |
        |<<object>>/*keyword*/ <<sample10>>/*class*/ {
        |  <<def>>/*keyword*/ <<main>>/*method*/(<<args>>/*parameter*/<<:>>/*operator*/ <<Array>>/*class*/[<<String>>/*type*/]) <<=>>/*operator*/{
        |    <<println>>/*method*/(
        |     (<<ab>>/*class*/.<<iVar>>/*variable*/  <<+>>/*operator,abstract*/ <<ab>>/*class*/.<<iVal>>/*variable,readonly*/).<<toString>>/*method*/
        |    )
        |  }
        |}
        |""".stripMargin,
    // In Scala 3 `+` is not abstract
    compat = Map(
      "3" -> s"""|
                 |<<object>>/*keyword*/ <<ab>>/*class*/ {
                 |  <<var>>/*keyword*/  <<iVar>>/*variable*/<<:>>/*operator*/<<Int>>/*class,abstract*/ <<=>>/*operator*/ <<1>>/*number*/
                 |  <<val>>/*keyword*/  <<iVal>>/*variable,readonly*/<<:>>/*operator*/<<Double>>/*class,abstract*/ <<=>>/*operator*/ <<4.94065645841246544e-324d>>/*number*/
                 |  <<val>>/*keyword*/  <<fVal>>/*variable,readonly*/<<:>>/*operator*/<<Float>>/*class,abstract*/ <<=>>/*operator*/ <<1.40129846432481707e-45>>/*number*/
                 |  <<val>>/*keyword*/  <<lVal>>/*variable,readonly*/<<:>>/*operator*/<<Long>>/*class,abstract*/ <<=>>/*operator*/ <<9223372036854775807L>>/*number*/
                 |}
                 |
                 |<<object>>/*keyword*/ <<sample10>>/*class*/ {
                 |  <<def>>/*keyword*/ <<main>>/*method*/(<<args>>/*parameter*/<<:>>/*operator*/ <<Array>>/*class*/[<<String>>/*type*/]) <<=>>/*operator*/{
                 |    <<println>>/*method*/(
                 |     (<<ab>>/*class*/.<<iVar>>/*variable*/  <<+>>/*operator*/ <<ab>>/*class*/.<<iVal>>/*variable,readonly*/).<<toString>>/*method*/
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
        |  <<def>>/*keyword*/ <<main>>/*method*/(<<args>>/*parameter*/<<:>>/*operator*/ <<Array>>/*class*/[<<String>>/*type*/]) <<=>>/*operator*/{
        |      <<val>>/*keyword*/ <<itr>>/*variable,readonly*/ <<=>>/*operator*/ <<new>>/*keyword*/ <<IntIterator>>/*class*/(<<5>>/*number*/)
        |      <<var>>/*keyword*/ <<str>>/*variable*/ <<=>>/*operator*/ <<itr>>/*variable,readonly*/.<<next>>/*method*/().<<toString>>/*method*/  <<+>>/*operator*/ <<",">>/*string*/
        |          <<str>>/*variable*/ <<+=>>/*operator*/ <<itr>>/*variable,readonly*/.<<next>>/*method*/().<<toString>>/*method*/
        |      <<println>>/*method*/(<<"count:">>/*string*/ <<+>>/*operator*/<<str>>/*variable*/)
        |  }
        |
        |  <<trait>>/*keyword*/ <<Iterator>>/*interface,abstract*/[<<A>>/*typeParameter,abstract*/] {
        |    <<def>>/*keyword*/ <<next>>/*method,abstract*/()<<:>>/*operator*/ <<A>>/*typeParameter,abstract*/
        |  }
        |
        |  <<abstract>>/*modifier*/ <<class>>/*keyword*/ <<hasLogger>>/*class,abstract*/ {
        |    <<def>>/*keyword*/ <<log>>/*method*/(<<str>>/*parameter*/<<:>>/*operator*/<<String>>/*type*/) <<=>>/*operator*/ {<<println>>/*method*/(<<str>>/*parameter*/)}
        |  }
        |
        |  <<class>>/*keyword*/ <<IntIterator>>/*class*/(<<to>>/*variable,readonly*/<<:>>/*operator*/ <<Int>>/*class,abstract*/)
        |  <<extends>>/*keyword*/ <<hasLogger>>/*class,abstract*/ <<with>>/*keyword*/ <<Iterator>>/*interface,abstract*/[<<Int>>/*class,abstract*/]  {
        |    <<private>>/*modifier*/ <<var>>/*keyword*/ <<current>>/*variable*/ <<=>>/*operator*/ <<0>>/*number*/
        |    <<override>>/*modifier*/ <<def>>/*keyword*/ <<next>>/*method*/()<<:>>/*operator*/ <<Int>>/*class,abstract*/ <<=>>/*operator*/ {
        |      <<if>>/*keyword*/ (<<current>>/*variable*/ <<<>>/*operator,abstract*/ <<to>>/*variable,readonly*/) {
        |        <<log>>/*method*/(<<"main">>/*string*/)
        |        <<val>>/*keyword*/ <<t>>/*variable,readonly*/ <<=>>/*operator*/ <<current>>/*variable*/
        |        <<current>>/*variable*/ <<=>>/*operator*/ <<current>>/*variable*/  <<+>>/*operator,abstract*/ <<1>>/*number*/
        |        <<t>>/*variable,readonly*/
        |      } <<else>>/*keyword*/ <<0>>/*number*/
        |    }
        |  }
        |}
        |
        |
        |""".stripMargin,
    // In Scala 3 `+` and trait methods are not abstract
    compat = Map(
      "3" ->
        s"""|
            |<<package>>/*keyword*/ <<a>>/*namespace*/.<<b>>/*namespace*/
            |<<object>>/*keyword*/ <<Sample5>>/*class*/ {
            |
            |  <<def>>/*keyword*/ <<main>>/*method*/(<<args>>/*parameter*/<<:>>/*operator*/ <<Array>>/*class*/[<<String>>/*type*/]) <<=>>/*operator*/{
            |      <<val>>/*keyword*/ <<itr>>/*variable,readonly*/ <<=>>/*operator*/ <<new>>/*keyword*/ <<IntIterator>>/*class*/(<<5>>/*number*/)
            |      <<var>>/*keyword*/ <<str>>/*variable*/ <<=>>/*operator*/ <<itr>>/*variable,readonly*/.<<next>>/*method*/().<<toString>>/*method*/  <<+>>/*operator*/ <<",">>/*string*/
            |          <<str>>/*variable*/ <<+=>>/*operator*/ <<itr>>/*variable,readonly*/.<<next>>/*method*/().<<toString>>/*method*/
            |      <<println>>/*method*/(<<"count:">>/*string*/ <<+>>/*operator*/<<str>>/*variable*/)
            |  }
            |
            |  <<trait>>/*keyword*/ <<Iterator>>/*interface,abstract*/[<<A>>/*typeParameter,abstract*/] {
            |    <<def>>/*keyword*/ <<next>>/*method*/()<<:>>/*operator*/ <<A>>/*typeParameter,abstract*/
            |  }
            |
            |  <<abstract>>/*modifier*/ <<class>>/*keyword*/ <<hasLogger>>/*class,abstract*/ {
            |    <<def>>/*keyword*/ <<log>>/*method*/(<<str>>/*parameter*/<<:>>/*operator*/<<String>>/*type*/) <<=>>/*operator*/ {<<println>>/*method*/(<<str>>/*parameter*/)}
            |  }
            |
            |  <<class>>/*keyword*/ <<IntIterator>>/*class*/(<<to>>/*variable,readonly*/<<:>>/*operator*/ <<Int>>/*class,abstract*/)
            |  <<extends>>/*keyword*/ <<hasLogger>>/*class,abstract*/ <<with>>/*keyword*/ <<Iterator>>/*interface,abstract*/[<<Int>>/*class,abstract*/]  {
            |    <<private>>/*modifier*/ <<var>>/*keyword*/ <<current>>/*variable*/ <<=>>/*operator*/ <<0>>/*number*/
            |    <<override>>/*modifier*/ <<def>>/*keyword*/ <<next>>/*method*/()<<:>>/*operator*/ <<Int>>/*class,abstract*/ <<=>>/*operator*/ {
            |      <<if>>/*keyword*/ (<<current>>/*variable*/ <<<>>/*operator*/ <<to>>/*variable,readonly*/) {
            |        <<log>>/*method*/(<<"main">>/*string*/)
            |        <<val>>/*keyword*/ <<t>>/*variable,readonly*/ <<=>>/*operator*/ <<current>>/*variable*/
            |        <<current>>/*variable*/ <<=>>/*operator*/ <<current>>/*variable*/  <<+>>/*operator*/ <<1>>/*number*/
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
        |  <<def>>/*keyword*/ <<oldMethod>>/*method,deprecated*/(<<x>>/*parameter*/<<:>>/*operator*/ <<Int>>/*class,abstract*/) <<=>>/*operator*/ <<x>>/*parameter*/
        |
        |  <<def>>/*keyword*/ <<main>>/*method*/(<<args>>/*parameter*/<<:>>/*operator*/ <<Array>>/*class*/[<<String>>/*type*/]) <<=>>/*operator*/{
        |    <<val>>/*keyword*/ <<str>>/*variable,readonly*/ <<=>>/*operator*/ <<oldMethod>>/*method,deprecated*/(<<2>>/*number*/).<<toString>>/*method*/
        |     <<println>>/*method*/(<<"Hello, world!">>/*string*/ <<+>>/*operator*/ <<str>>/*variable,readonly*/)
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
        |  <<def>>/*keyword*/ <<sqrtplus1>>/*method*/(<<x>>/*parameter*/<<:>>/*operator*/ <<Int>>/*class,abstract*/)
        |     <<=>>/*operator*/ <<sqrt>>/*method*/(<<x>>/*parameter*/).<<toString>>/*method*/()
        |
        |  <<def>>/*keyword*/ <<main>>/*method*/(<<args>>/*parameter*/<<:>>/*operator*/ <<Array>>/*class*/[<<String>>/*type*/]) <<=>>/*operator*/{
        |    <<println>>/*method*/(<<"Hello, world! : ">>/*string*/  <<+>>/*operator*/ <<sqrtplus1>>/*method*/(<<2>>/*number*/))
        |  }
        |}
        |
        |""".stripMargin,
  )

  check(
    "anonymous-class",
    s"""|<<object>>/*keyword*/ <<A>>/*class*/ {
        |  <<trait>>/*keyword*/ <<Methodable>>/*interface,abstract*/[<<T>>/*typeParameter,abstract*/] {
        |    <<def>>/*keyword*/ <<method>>/*method,abstract*/(<<asf>>/*parameter*/<<:>>/*operator*/ <<T>>/*typeParameter,abstract*/)<<:>>/*operator*/ <<Int>>/*class,abstract*/
        |  }
        |
        |  <<abstract>>/*modifier*/ <<class>>/*keyword*/ <<Alphabet>>/*class,abstract*/(<<alp>>/*variable,readonly*/<<:>>/*operator*/ <<Int>>/*class,abstract*/) <<extends>>/*keyword*/ <<Methodable>>/*interface,abstract*/[<<String>>/*type*/] {
        |    <<def>>/*keyword*/ <<method>>/*method*/(<<adf>>/*parameter*/<<:>>/*operator*/ <<String>>/*type*/) <<=>>/*operator*/ <<123>>/*number*/
        |  }
        |  <<val>>/*keyword*/ <<a>>/*variable,readonly*/ <<=>>/*operator*/ <<new>>/*keyword*/ <<Alphabet>>/*class,abstract*/(<<alp>>/*parameter*/ <<=>>/*operator*/ <<10>>/*number*/) {
        |    <<override>>/*modifier*/ <<def>>/*keyword*/ <<method>>/*method*/(<<adf>>/*parameter*/<<:>>/*operator*/ <<String>>/*type*/)<<:>>/*operator*/ <<Int>>/*class,abstract*/ <<=>>/*operator*/ <<321>>/*number*/
        |  }
        |}""".stripMargin,
    // In Scala 3 methods in `trait` are not abstract
    compat = Map(
      "3" -> s"""|<<object>>/*keyword*/ <<A>>/*class*/ {
                 |  <<trait>>/*keyword*/ <<Methodable>>/*interface,abstract*/[<<T>>/*typeParameter,abstract*/] {
                 |    <<def>>/*keyword*/ <<method>>/*method*/(<<asf>>/*parameter*/<<:>>/*operator*/ <<T>>/*typeParameter,abstract*/)<<:>>/*operator*/ <<Int>>/*class,abstract*/
                 |  }
                 |
                 |  <<abstract>>/*modifier*/ <<class>>/*keyword*/ <<Alphabet>>/*class,abstract*/(<<alp>>/*variable,readonly*/<<:>>/*operator*/ <<Int>>/*class,abstract*/) <<extends>>/*keyword*/ <<Methodable>>/*interface,abstract*/[<<String>>/*type*/] {
                 |    <<def>>/*keyword*/ <<method>>/*method*/(<<adf>>/*parameter*/<<:>>/*operator*/ <<String>>/*type*/) <<=>>/*operator*/ <<123>>/*number*/
                 |  }
                 |  <<val>>/*keyword*/ <<a>>/*variable,readonly*/ <<=>>/*operator*/ <<new>>/*keyword*/ <<Alphabet>>/*class,abstract*/(<<alp>>/*parameter*/ <<=>>/*operator*/ <<10>>/*number*/) {
                 |    <<override>>/*modifier*/ <<def>>/*keyword*/ <<method>>/*method*/(<<adf>>/*parameter*/<<:>>/*operator*/ <<String>>/*type*/)<<:>>/*operator*/ <<Int>>/*class,abstract*/ <<=>>/*operator*/ <<321>>/*number*/
                 |  }
                 |}""".stripMargin
    ),
  )

  check(
    "import-rename",
    s"""|<<package>>/*keyword*/ <<example>>/*namespace*/
        |
        |<<import>>/*keyword*/ <<util>>/*namespace*/.{<<Failure>>/*class*/ <<=>>>/*operator*/ <<NoBad>>/*class*/}
        |<<import>>/*keyword*/ <<math>>/*namespace*/.{<<floor>>/*method*/ <<=>>>/*operator*/ <<_>>/*variable*/, <<_>>/*variable*/}
        |
        |<<class>>/*keyword*/ <<Imports>>/*class*/ {
        |  <<// rename reference>>/*comment*/
        |  <<NoBad>>/*class*/(<<null>>/*keyword*/)
        |  <<max>>/*method*/(<<1>>/*number*/, <<2>>/*number*/)
        |}""".stripMargin,
  )

}
