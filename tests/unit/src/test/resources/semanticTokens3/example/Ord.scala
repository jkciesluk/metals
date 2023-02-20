<<package>>/*keyword*/ <<example>>/*namespace*/

<<trait>>/*keyword*/ <<Ord>>/*interface,abstract*/[<<T>>/*typeParameter,abstract*/]<<:>>/*operator*/
  <<def>>/*keyword*/ <<compare>>/*method*/(<<x>>/*parameter*/<<:>>/*operator*/ <<T>>/*typeParameter,abstract*/, <<y>>/*parameter*/<<:>>/*operator*/ <<T>>/*typeParameter,abstract*/)<<:>>/*operator*/ <<Int>>/*class,abstract*/

<<given>>/*keyword*/ <<intOrd>>/*class*/<<:>>/*operator*/ <<Ord>>/*interface,abstract*/[<<Int>>/*class,abstract*/] <<with>>/*keyword*/
  <<def>>/*keyword*/ <<compare>>/*method*/(<<x>>/*parameter*/<<:>>/*operator*/ <<Int>>/*class,abstract*/, <<y>>/*parameter*/<<:>>/*operator*/ <<Int>>/*class,abstract*/) <<=>>/*operator*/
    <<if>>/*keyword*/ <<x>>/*parameter*/ <<<>>/*operator*/ <<y>>/*parameter*/ <<then>>/*keyword*/ <<->>/*operator*/<<1>>/*number*/ <<else>>/*keyword*/ <<if>>/*keyword*/ <<x>>/*parameter*/ <<>>>/*operator*/ <<y>>/*parameter*/ <<then>>/*keyword*/ <<+>>/*operator*/<<1>>/*number*/ <<else>>/*keyword*/ <<0>>/*number*/

<<given>>/*keyword*/ <<Ord>>/*interface,abstract*/[<<String>>/*type*/] <<with>>/*keyword*/
  <<def>>/*keyword*/ <<compare>>/*method*/(<<x>>/*parameter*/<<:>>/*operator*/ <<String>>/*type*/, <<y>>/*parameter*/<<:>>/*operator*/ <<String>>/*type*/) <<=>>/*operator*/
    <<x>>/*parameter*/.<<compare>>/*method*/(<<y>>/*parameter*/)