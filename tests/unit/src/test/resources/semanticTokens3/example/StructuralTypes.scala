<<package>>/*keyword*/ <<example>>/*namespace*/

<<import>>/*keyword*/ <<reflect>>/*namespace*/.<<Selectable>>/*class*/.<<reflectiveSelectable>>/*method*/

<<object>>/*keyword*/ <<StructuralTypes>>/*class*/<<:>>/*operator*/
  <<type>>/*keyword*/ <<User>>/*type*/ <<=>>/*operator*/ {
    <<def>>/*keyword*/ <<name>>/*method*/<<:>>/*operator*/ <<String>>/*type*/
    <<def>>/*keyword*/ <<age>>/*method*/<<:>>/*operator*/ <<Int>>/*class,abstract*/
  }

  <<val>>/*keyword*/ <<user>>/*variable,readonly*/ <<=>>/*operator*/ <<null>>/*keyword*/.<<asInstanceOf>>/*method*/[<<User>>/*type*/]
  <<user>>/*variable,readonly*/.name
  <<user>>/*variable,readonly*/.age

  <<val>>/*keyword*/ <<V>>/*variable,readonly*/<<:>>/*operator*/ <<Object>>/*class*/ {
    <<def>>/*keyword*/ <<scalameta>>/*method*/<<:>>/*operator*/ <<String>>/*type*/
  } <<=>>/*operator*/ <<new>>/*keyword*/<<:>>/*operator*/
    <<def>>/*keyword*/ <<scalameta>>/*method*/ <<=>>/*operator*/ <<"4.0">>/*string*/
  <<V>>/*variable,readonly*/.scalameta
<<end>>/*keyword*/ StructuralTypes