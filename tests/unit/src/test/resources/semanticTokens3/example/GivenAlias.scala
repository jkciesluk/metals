<<package>>/*keyword*/ <<example>>/*namespace*/

<<given>>/*keyword*/ <<intValue>>/*variable,readonly*/<<:>>/*operator*/ <<Int>>/*class,abstract*/ <<=>>/*operator*/ <<4>>/*number*/
<<given>>/*keyword*/ <<String>>/*type*/ <<=>>/*operator*/ <<"str">>/*string*/
<<given>>/*keyword*/ (<<using>>/*keyword*/ <<i>>/*parameter*/<<:>>/*operator*/ <<Int>>/*class,abstract*/)<<:>>/*operator*/ <<Double>>/*class,abstract*/ <<=>>/*operator*/ <<4.0>>/*number*/
<<given>>/*keyword*/ [<<T>>/*typeParameter,abstract*/]<<:>>/*operator*/ <<List>>/*type*/[<<T>>/*typeParameter,abstract*/] <<=>>/*operator*/ <<Nil>>/*variable,readonly*/
<<given>>/*keyword*/ <<given_Char>>/*variable,readonly*/<<:>>/*operator*/ <<Char>>/*class,abstract*/ <<=>>/*operator*/ <<'?'>>/*string*/
<<given>>/*keyword*/ <<`given_Float`>>/*variable,readonly*/<<:>>/*operator*/ <<Float>>/*class,abstract*/ <<=>>/*operator*/ <<3.0>>/*number*/
<<given>>/*keyword*/ <<`* *`>>/*operator*/ <<:>>/*operator*/ <<Long>>/*class,abstract*/ <<=>>/*operator*/ <<5>>/*number*/

<<def>>/*keyword*/ <<method>>/*method*/(<<using>>/*keyword*/ <<Int>>/*class,abstract*/) <<=>>/*operator*/ <<"">>/*string*/

<<object>>/*keyword*/ <<X>>/*class*/<<:>>/*operator*/
  <<given>>/*keyword*/ <<Double>>/*class,abstract*/ <<=>>/*operator*/ <<4.0>>/*number*/
  <<val>>/*keyword*/ <<double>>/*variable,readonly*/ <<=>>/*operator*/ <<given_Double>>/*variable,readonly*/

  <<given>>/*keyword*/ <<of>>/*method*/[<<A>>/*typeParameter,abstract*/]<<:>>/*operator*/ <<Option>>/*class,abstract*/[<<A>>/*typeParameter,abstract*/] <<=>>/*operator*/ <<???>>/*operator*/

<<trait>>/*keyword*/ <<Xg>>/*interface,abstract*/<<:>>/*operator*/
  <<def>>/*keyword*/ <<doX>>/*method*/<<:>>/*operator*/ <<Int>>/*class,abstract*/

<<trait>>/*keyword*/ <<Yg>>/*interface,abstract*/<<:>>/*operator*/
  <<def>>/*keyword*/ <<doY>>/*method*/<<:>>/*operator*/ <<String>>/*type*/

<<trait>>/*keyword*/ <<Zg>>/*interface,abstract*/[<<T>>/*typeParameter,abstract*/]<<:>>/*operator*/
  <<def>>/*keyword*/ <<doZ>>/*method*/<<:>>/*operator*/ <<List>>/*type*/[<<T>>/*typeParameter,abstract*/]

<<given>>/*keyword*/ <<Xg>>/*interface,abstract*/ <<with>>/*keyword*/
  <<def>>/*keyword*/ <<doX>>/*method*/ <<=>>/*operator*/ <<7>>/*number*/

<<given>>/*keyword*/ (<<using>>/*keyword*/ <<Xg>>/*interface,abstract*/)<<:>>/*operator*/ <<Yg>>/*interface,abstract*/ <<with>>/*keyword*/
  <<def>>/*keyword*/ <<doY>>/*method*/ <<=>>/*operator*/ <<"7">>/*string*/

<<given>>/*keyword*/ [<<T>>/*typeParameter,abstract*/]<<:>>/*operator*/ <<Zg>>/*interface,abstract*/[<<T>>/*typeParameter,abstract*/] <<with>>/*keyword*/
  <<def>>/*keyword*/ <<doZ>>/*method*/<<:>>/*operator*/ <<List>>/*type*/[<<T>>/*typeParameter,abstract*/] <<=>>/*operator*/ <<Nil>>/*variable,readonly*/

<<val>>/*keyword*/ <<a>>/*variable,readonly*/ <<=>>/*operator*/ <<intValue>>/*variable,readonly*/
<<val>>/*keyword*/ <<b>>/*variable,readonly*/ <<=>>/*operator*/ <<given_String>>/*variable,readonly*/
<<val>>/*keyword*/ <<c>>/*variable,readonly*/ <<=>>/*operator*/ <<X>>/*class*/.<<given_Double>>/*variable,readonly*/
<<val>>/*keyword*/ <<d>>/*variable,readonly*/ <<=>>/*operator*/ <<given_List_T>>/*method*/[<<Int>>/*class,abstract*/]
<<val>>/*keyword*/ <<e>>/*variable,readonly*/ <<=>>/*operator*/ <<given_Char>>/*variable,readonly*/
<<val>>/*keyword*/ <<f>>/*variable,readonly*/ <<=>>/*operator*/ <<given_Float>>/*variable,readonly*/
<<val>>/*keyword*/ <<g>>/*variable,readonly*/ <<=>>/*operator*/ <<`* *`>>/*operator*/
<<val>>/*keyword*/ <<i>>/*variable,readonly*/ <<=>>/*operator*/ <<X>>/*class*/.<<of>>/*method*/[<<Int>>/*class,abstract*/]
<<val>>/*keyword*/ <<x>>/*variable,readonly*/ <<=>>/*operator*/ <<given_Xg>>/*class*/
<<val>>/*keyword*/ <<y>>/*variable,readonly*/ <<=>>/*operator*/ <<given_Yg>>/*method*/
<<val>>/*keyword*/ <<z>>/*variable,readonly*/ <<=>>/*operator*/ <<given_Zg_T>>/*method*/[<<String>>/*type*/]