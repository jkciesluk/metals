<<package>>/*keyword*/ <<example>>/*namespace*/.<<nested>>/*namespace*/

<<trait>>/*keyword*/ <<LocalDeclarations>>/*interface,abstract*/<<:>>/*operator*/
  <<def>>/*keyword*/ <<foo>>/*method*/()<<:>>/*operator*/ <<Unit>>/*class,abstract*/

<<trait>>/*keyword*/ <<Foo>>/*interface,abstract*/<<:>>/*operator*/
  <<val>>/*keyword*/ <<y>>/*variable,readonly*/ <<=>>/*operator*/ <<3>>/*number*/

<<object>>/*keyword*/ <<LocalDeclarations>>/*class*/<<:>>/*operator*/
  <<def>>/*keyword*/ <<create>>/*method*/()<<:>>/*operator*/ <<LocalDeclarations>>/*interface,abstract*/ <<=>>/*operator*/
    <<def>>/*keyword*/ <<bar>>/*method*/()<<:>>/*operator*/ <<Unit>>/*class,abstract*/ <<=>>/*operator*/ ()

    <<val>>/*keyword*/ <<x>>/*variable,readonly*/ <<=>>/*operator*/ <<new>>/*keyword*/<<:>>/*operator*/
      <<val>>/*keyword*/ <<x>>/*variable,readonly*/ <<=>>/*operator*/ <<2>>/*number*/

    <<val>>/*keyword*/ <<y>>/*variable,readonly*/ <<=>>/*operator*/ <<new>>/*keyword*/ <<Foo>>/*interface,abstract*/ {}

    <<y>>/*variable,readonly*/.<<y>>/*variable,readonly*/

    <<new>>/*keyword*/ <<LocalDeclarations>>/*interface,abstract*/ <<with>>/*keyword*/ <<Foo>>/*interface,abstract*/<<:>>/*operator*/
      <<override>>/*modifier*/ <<def>>/*keyword*/ <<foo>>/*method*/()<<:>>/*operator*/ <<Unit>>/*class,abstract*/ <<=>>/*operator*/ <<bar>>/*method*/()