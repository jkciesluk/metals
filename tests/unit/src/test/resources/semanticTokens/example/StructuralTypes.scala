<<package>>/*keyword*/ <<example>>/*namespace*/

<<object>>/*keyword*/ <<StructuralTypes>>/*class*/ {
  <<type>>/*keyword*/ <<User>>/*type*/ <<=>>/*operator*/ {
    <<def>>/*keyword*/ <<name>>/*method,abstract*/<<:>>/*operator*/ <<String>>/*type*/
    <<def>>/*keyword*/ <<age>>/*method,abstract*/<<:>>/*operator*/ <<Int>>/*class,abstract*/
  }

  <<val>>/*keyword*/ <<user>>/*variable,readonly*/ <<=>>/*operator*/ <<null>>/*keyword*/.<<asInstanceOf>>/*method*/[<<User>>/*type*/]
  <<user>>/*variable,readonly*/.<<name>>/*method,abstract*/
  <<user>>/*variable,readonly*/.<<age>>/*method,abstract*/

  <<val>>/*keyword*/ <<V>>/*variable,readonly*/<<:>>/*operator*/ <<Object>>/*class*/ {
    <<def>>/*keyword*/ <<scalameta>>/*method,abstract*/<<:>>/*operator*/ <<String>>/*type*/
  } <<=>>/*operator*/ <<new>>/*keyword*/ {
    <<def>>/*keyword*/ <<scalameta>>/*method*/ <<=>>/*operator*/ <<"4.0">>/*string*/
  }
  <<V>>/*variable,readonly*/.<<scalameta>>/*method,abstract*/
}