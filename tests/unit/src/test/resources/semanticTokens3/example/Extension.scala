<<package>>/*keyword*/ <<example>>/*namespace*/

<<extension>>/*keyword*/ (<<i>>/*parameter*/<<:>>/*operator*/ <<Int>>/*class,abstract*/) <<def>>/*keyword*/ <<asString>>/*method*/<<:>>/*operator*/ <<String>>/*type*/ <<=>>/*operator*/ <<i>>/*parameter*/.<<toString>>/*method*/

<<extension>>/*keyword*/ (<<s>>/*parameter*/<<:>>/*operator*/ <<String>>/*type*/)
  <<def>>/*keyword*/ <<asInt>>/*method*/<<:>>/*operator*/ <<Int>>/*class,abstract*/ <<=>>/*operator*/ <<s>>/*parameter*/.<<toInt>>/*method*/
  <<def>>/*keyword*/ <<double>>/*method*/<<:>>/*operator*/ <<String>>/*type*/ <<=>>/*operator*/ <<s>>/*parameter*/ <<*>>/*operator*/ <<2>>/*number*/

<<trait>>/*keyword*/ <<AbstractExtension>>/*interface,abstract*/<<:>>/*operator*/
  <<extension>>/*keyword*/ (<<d>>/*parameter*/<<:>>/*operator*/ <<Double>>/*class,abstract*/)
    <<def>>/*keyword*/ <<abc>>/*method*/<<:>>/*operator*/ <<String>>/*type*/