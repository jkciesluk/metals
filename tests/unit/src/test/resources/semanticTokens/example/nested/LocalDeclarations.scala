<<package>>/*keyword*/ <<example>>/*namespace*/.<<nested>>/*namespace*/

<<trait>>/*keyword*/ <<LocalDeclarations>>/*interface,abstract*/ {
  <<def>>/*keyword*/ <<foo>>/*method,abstract*/()<<:>>/*operator*/ <<Unit>>/*class,abstract*/
}

<<trait>>/*keyword*/ <<Foo>>/*interface,abstract*/ {
  <<val>>/*keyword*/ <<y>>/*variable,readonly*/ <<=>>/*operator*/ <<3>>/*number*/
}

<<object>>/*keyword*/ <<LocalDeclarations>>/*class*/ {
  <<def>>/*keyword*/ <<create>>/*method*/()<<:>>/*operator*/ <<LocalDeclarations>>/*interface,abstract*/ <<=>>/*operator*/ {
    <<def>>/*keyword*/ <<bar>>/*method*/()<<:>>/*operator*/ <<Unit>>/*class,abstract*/ <<=>>/*operator*/ ()

    <<val>>/*keyword*/ <<x>>/*variable,readonly*/ <<=>>/*operator*/ <<new>>/*keyword*/ {
      <<val>>/*keyword*/ <<x>>/*variable,readonly*/ <<=>>/*operator*/ <<2>>/*number*/
    }

    <<val>>/*keyword*/ <<y>>/*variable,readonly*/ <<=>>/*operator*/ <<new>>/*keyword*/ <<Foo>>/*interface,abstract*/ {}

    <<x>>/*variable,readonly*/.<<x>>/*variable,readonly*/ <<+>>/*operator,abstract*/ <<y>>/*variable,readonly*/.<<y>>/*variable,readonly*/

    <<new>>/*keyword*/ <<LocalDeclarations>>/*interface,abstract*/ <<with>>/*keyword*/ <<Foo>>/*interface,abstract*/ {
      <<override>>/*modifier*/ <<def>>/*keyword*/ <<foo>>/*method*/()<<:>>/*operator*/ <<Unit>>/*class,abstract*/ <<=>>/*operator*/ <<bar>>/*method*/()
    }

  }
}