<<package>>/*keyword*/ <<example>>/*namespace*/

<<object>>/*keyword*/ <<ImplicitClasses>>/*class*/ {
  <<implicit>>/*modifier*/ <<class>>/*keyword*/ <<Xtension>>/*class*/(<<number>>/*variable,readonly*/<<:>>/*operator*/ <<Int>>/*class,abstract*/) {
    <<def>>/*keyword*/ <<increment>>/*method*/<<:>>/*operator*/ <<Int>>/*class,abstract*/ <<=>>/*operator*/ <<number>>/*variable,readonly*/ <<+>>/*operator*/ <<1>>/*number*/
  }
  <<implicit>>/*modifier*/ <<class>>/*keyword*/ <<XtensionAnyVal>>/*class*/(<<private>>/*modifier*/ <<val>>/*keyword*/ <<number>>/*variable,readonly*/<<:>>/*operator*/ <<Int>>/*class,abstract*/) <<extends>>/*keyword*/ <<AnyVal>>/*class,abstract*/ {
    <<def>>/*keyword*/ <<double>>/*method*/<<:>>/*operator*/ <<Int>>/*class,abstract*/ <<=>>/*operator*/ <<number>>/*variable,readonly*/ <<*>>/*operator*/ <<2>>/*number*/
  }
}