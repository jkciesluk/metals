<<package>>/*keyword*/ <<example>>/*namespace*/

<<class>>/*keyword*/ <<Miscellaneous>>/*class*/ {
  <<// backtick identifier>>/*comment*/
  <<val>>/*keyword*/ <<`a b`>>/*variable,readonly*/ <<=>>/*operator*/ <<42>>/*number*/

  <<// block with only wildcard value>>/*comment*/
  <<def>>/*keyword*/ <<apply>>/*method*/()<<:>>/*operator*/ <<Unit>>/*class,abstract*/ <<=>>/*operator*/ {
    <<val>>/*keyword*/ <<_>>/*variable*/ <<=>>/*operator*/ <<42>>/*number*/
  }
  <<// infix + inferred apply/implicits/tparams>>/*comment*/
  (<<List>>/*variable,readonly*/(<<1>>/*number*/)
    .<<map>>/*method*/(<<_>>/*variable*/ <<+>>/*operator,abstract*/ <<1>>/*number*/)
    <<++>>/*operator*/
      <<List>>/*variable,readonly*/(<<3>>/*number*/))
}