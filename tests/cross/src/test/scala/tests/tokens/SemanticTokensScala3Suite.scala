package tests.tokens

import tests.BaseSemanticTokensSuite

class SemanticTokensScala3Suite extends BaseSemanticTokensSuite {
  override protected def ignoreScalaVersion: Option[IgnoreScalaVersion] = Some(
    IgnoreScala2
  )

  check(
    "enum",
    s"""|<<package>>/*keyword*/ <<example>>/*namespace*/
        |
        |<<enum>>/*keyword*/ <<FooEnum>>/*enum,abstract*/<<:>>/*operator*/
        |  <<case>>/*keyword*/ <<Bar>>/*enum*/, <<Baz>>/*enum*/
        |<<object>>/*keyword*/ <<FooEnum>>/*class*/
        |""".stripMargin,
  )

  check(
    "enum1",
    s"""|<<package>>/*keyword*/ <<example>>/*namespace*/
        |
        |<<enum>>/*keyword*/ <<FooEnum>>/*enum,abstract*/<<:>>/*operator*/
        |  <<case>>/*keyword*/ <<A>>/*enum*/(<<a>>/*variable,readonly*/<<:>>/*operator*/ <<Int>>/*class,abstract*/)
        |  <<case>>/*keyword*/ <<B>>/*enum*/(<<a>>/*variable,readonly*/<<:>>/*operator*/ <<Int>>/*class,abstract*/, <<b>>/*variable,readonly*/<<:>>/*operator*/ <<Int>>/*class,abstract*/)
        |  <<case>>/*keyword*/ <<C>>/*enum*/(<<a>>/*variable,readonly*/<<:>>/*operator*/ <<Int>>/*class,abstract*/, <<b>>/*variable,readonly*/<<:>>/*operator*/ <<Int>>/*class,abstract*/, <<c>>/*variable,readonly*/<<:>>/*operator*/ <<Int>>/*class,abstract*/)
        |
        |""".stripMargin,
  )

  // Issue: Sequential parameters are not highlighted
  // https://github.com/scalameta/metals/issues/4985
  check(
    "named-arguments",
    s"""|<<package>>/*keyword*/ <<example>>/*namespace*/
        |
        |<<def>>/*keyword*/ <<m>>/*method*/(<<xs>>/*parameter*/<<:>>/*operator*/ <<Int>>/*class,abstract*/<<*>>/*operator*/) <<=>>/*operator*/ <<xs>>/*parameter*/.<<map>>/*method*/(<<_>>/*variable*/ <<+>>/*operator*/ <<1>>/*number*/)
        |<<val>>/*keyword*/ <<a>>/*variable,readonly*/ <<=>>/*operator*/ <<m>>/*method*/(xs <<=>>/*operator*/ <<1>>/*number*/,<<2>>/*number*/,<<3>>/*number*/)
        |""".stripMargin,
  )

  // Issue: Structural types are not highlighted
  // https://github.com/scalameta/metals/issues/4984
  check(
    "structural-types",
    s"""|<<package>>/*keyword*/ <<example>>/*namespace*/
        |
        |<<import>>/*keyword*/ <<reflect>>/*namespace*/.<<Selectable>>/*class*/.<<reflectiveSelectable>>/*method*/
        |
        |<<object>>/*keyword*/ <<StructuralTypes>>/*class*/<<:>>/*operator*/
        |  <<type>>/*keyword*/ <<User>>/*type*/ <<=>>/*operator*/ {
        |    <<def>>/*keyword*/ <<name>>/*method*/<<:>>/*operator*/ <<String>>/*type*/
        |    <<def>>/*keyword*/ <<age>>/*method*/<<:>>/*operator*/ <<Int>>/*class,abstract*/
        |  }
        |
        |  <<val>>/*keyword*/ <<user>>/*variable,readonly*/ <<=>>/*operator*/ <<null>>/*keyword*/.<<asInstanceOf>>/*method*/[<<User>>/*type*/]
        |  <<user>>/*variable,readonly*/.name
        |  <<user>>/*variable,readonly*/.age
        |
        |  <<val>>/*keyword*/ <<V>>/*variable,readonly*/<<:>>/*operator*/ <<Object>>/*class*/ {
        |    <<def>>/*keyword*/ <<scalameta>>/*method*/<<:>>/*operator*/ <<String>>/*type*/
        |  } <<=>>/*operator*/ <<new>>/*keyword*/<<:>>/*operator*/
        |    <<def>>/*keyword*/ <<scalameta>>/*method*/ <<=>>/*operator*/ <<"4.0">>/*string*/
        |  <<V>>/*variable,readonly*/.scalameta
        |<<end>>/*keyword*/ StructuralTypes
        |""".stripMargin,
  )

  check(
    "vars",
    s"""|<<package>>/*keyword*/ <<example>>/*namespace*/
        |
        |<<object>>/*keyword*/ <<A>>/*class*/ {
        |  <<val>>/*keyword*/ <<a>>/*variable,readonly*/ <<=>>/*operator*/ <<1>>/*number*/
        |  <<var>>/*keyword*/ <<b>>/*variable*/ <<=>>/*operator*/ <<2>>/*number*/
        |  <<val>>/*keyword*/ <<c>>/*variable,readonly*/ <<=>>/*operator*/ <<List>>/*variable,readonly*/(<<1>>/*number*/,<<a>>/*variable,readonly*/,<<b>>/*variable*/)
        |  <<b>>/*variable*/ <<=>>/*operator*/ <<a>>/*variable,readonly*/
        |""".stripMargin,
  )

}
