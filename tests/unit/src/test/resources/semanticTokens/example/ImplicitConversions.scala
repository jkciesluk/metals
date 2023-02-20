<<package>>/*keyword*/ <<example>>/*namespace*/

<<class>>/*keyword*/ <<ImplicitConversions>>/*class*/ {
  <<implicit>>/*modifier*/ <<def>>/*keyword*/ <<string2Number>>/*method*/(
      <<string>>/*parameter*/<<:>>/*operator*/ <<String>>/*type*/
  )<<:>>/*operator*/ <<Int>>/*class,abstract*/ <<=>>/*operator*/ <<42>>/*number*/
  <<val>>/*keyword*/ <<message>>/*variable,readonly*/ <<=>>/*operator*/ <<"">>/*string*/
  <<val>>/*keyword*/ <<number>>/*variable,readonly*/ <<=>>/*operator*/ <<42>>/*number*/
  <<val>>/*keyword*/ <<tuple>>/*variable,readonly*/ <<=>>/*operator*/ (<<1>>/*number*/, <<2>>/*number*/)
  <<val>>/*keyword*/ <<char>>/*variable,readonly*/<<:>>/*operator*/ <<Char>>/*class,abstract*/ <<=>>/*operator*/ <<'a'>>/*string*/

  <<// extension methods>>/*comment*/
  <<message>>/*variable,readonly*/
    .<<stripSuffix>>/*method*/(<<"h">>/*string*/)
  <<tuple>>/*variable,readonly*/ <<+>>/*operator*/ <<"Hello">>/*string*/

  <<// implicit conversions>>/*comment*/
  <<val>>/*keyword*/ <<x>>/*variable,readonly*/<<:>>/*operator*/ <<Int>>/*class,abstract*/ <<=>>/*operator*/ <<message>>/*variable,readonly*/

  <<// interpolators>>/*comment*/
  <<s>>/*keyword*/<<">>/*string*/<<Hello >>/*string*/<<$>>/*keyword*/<<message>>/*variable,readonly*/<< >>/*string*/<<$>>/*keyword*/<<number>>/*variable,readonly*/<<">>/*string*/
  <<s>>/*keyword*/<<""">>/*string*/<<Hello>>/*string*/
<<     |>>/*string*/<<$>>/*keyword*/<<message>>/*variable,readonly*/<<>>/*string*/
<<     |>>/*string*/<<$>>/*keyword*/<<number>>/*variable,readonly*/<<""">>/*string*/.<<stripMargin>>/*method*/

  <<val>>/*keyword*/ <<a>>/*variable,readonly*/<<:>>/*operator*/ <<Int>>/*class,abstract*/ <<=>>/*operator*/ <<char>>/*variable,readonly*/
  <<val>>/*keyword*/ <<b>>/*variable,readonly*/<<:>>/*operator*/ <<Long>>/*class,abstract*/ <<=>>/*operator*/ <<char>>/*variable,readonly*/
}