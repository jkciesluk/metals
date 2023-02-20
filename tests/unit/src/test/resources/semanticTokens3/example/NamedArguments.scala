<<package>>/*keyword*/ <<example>>/*namespace*/

<<case>>/*keyword*/ <<class>>/*keyword*/ <<User>>/*class*/(
    <<name>>/*variable,readonly*/<<:>>/*operator*/ <<String>>/*type*/ <<=>>/*operator*/ {
      <<// assert default values have occurrences>>/*comment*/
      <<Map>>/*variable,readonly*/.<<toString>>/*method*/
    }
)
<<object>>/*keyword*/ <<NamedArguments>>/*class*/ {
  <<final>>/*modifier*/ <<val>>/*keyword*/ <<susan>>/*variable,readonly*/ <<=>>/*operator*/ <<"Susan">>/*string*/
  <<val>>/*keyword*/ <<user1>>/*variable,readonly*/ <<=>>/*operator*/
    <<User>>/*class*/
      .<<apply>>/*method*/(
        <<name>>/*parameter*/ <<=>>/*operator*/ <<"John">>/*string*/
      )
  <<val>>/*keyword*/ <<user2>>/*variable,readonly*/<<:>>/*operator*/ <<User>>/*class*/ <<=>>/*operator*/
    <<User>>/*class*/(
      <<name>>/*parameter*/ <<=>>/*operator*/ <<susan>>/*variable,readonly*/
    ).<<copy>>/*method*/(
      <<name>>/*parameter*/ <<=>>/*operator*/ <<susan>>/*variable,readonly*/
    )

  <<// anonymous classes>>/*comment*/
  <<@>>/*keyword*/<<deprecated>>/*class*/(
    <<message>>/*parameter*/ <<=>>/*operator*/ <<"a">>/*string*/,
    <<since>>/*parameter*/ <<=>>/*operator*/ <<susan>>/*variable,readonly*/,
  ) <<def>>/*keyword*/ <<b>>/*method,deprecated*/ <<=>>/*operator*/ <<1>>/*number*/

  <<// vararg>>/*comment*/
  <<List>>/*variable,readonly*/(
    elems <<=>>/*operator*/ <<2>>/*number*/
  )

}