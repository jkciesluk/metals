<<package>>/*keyword*/ <<example>>/*namespace*/

<<import>>/*keyword*/ <<io>>/*namespace*/.<<circe>>/*namespace*/.<<derivation>>/*namespace*/.<<annotations>>/*namespace*/.<<JsonCodec>>/*class*/

<<@>>/*keyword*/<<JsonCodec>>/*class*/
<<// FIXME: https://github.com/scalameta/scalameta/issues/1789>>/*comment*/
<<case>>/*keyword*/ <<class>>/*keyword*/ <<MacroAnnotation>>/*class*/(
    <<name>>/*variable,readonly*/: <<String>>/*type*/
) {
  <<def>>/*keyword*/ method = <<42>>/*number*/
}

<<object>>/*keyword*/ <<MacroAnnotations>>/*class*/ {
  <<import>>/*keyword*/ <<scala>>/*namespace*/.<<meta>>/*namespace*/.<<_>>/*variable*/
  <<// IntelliJ has never managed to goto definition for the inner classes from Trees.scala>>/*comment*/
  <<// due to the macro annotations.>>/*comment*/
  <<val>>/*keyword*/ <<x>>/*variable,readonly*/: <<Defn>>/*class*/.<<Class>>/*interface,abstract*/ = <<Defn>>/*class*/.<<Class>>/*class*/(
    <<Nil>>/*variable*/,
    <<Type>>/*class*/.<<Name>>/*class*/(<<"test">>/*string*/),
    <<Nil>>/*variable*/,
    <<Ctor>>/*class*/.<<Primary>>/*class*/(<<Nil>>/*variable*/, <<Term>>/*class*/.<<Name>>/*class*/(<<"this">>/*string*/), <<Nil>>/*variable*/),
    <<Template>>/*class*/(<<Nil>>/*variable*/, <<Nil>>/*variable*/, <<Self>>/*class*/(Name.Anonymous(), None), <<Nil>>/*variable*/),
  )
  <<val>>/*keyword*/ <<y>>/*variable,readonly*/: <<Mod>>/*class*/.<<Final>>/*interface,abstract*/ = <<Mod>>/*class*/.<<Final>>/*class*/()
}