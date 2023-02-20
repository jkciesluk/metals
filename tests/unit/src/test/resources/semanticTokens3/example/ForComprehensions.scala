<<package>>/*keyword*/ <<example>>/*namespace*/

<<class>>/*keyword*/ <<ForComprehensions>>/*class*/ {
  <<for>>/*keyword*/ {
    <<a>>/*parameter*/ <<<->>/*operator*/ <<List>>/*variable,readonly*/(<<1>>/*number*/)
    <<b>>/*parameter*/ <<<->>/*operator*/ <<List>>/*variable,readonly*/(<<a>>/*parameter*/)
    <<if>>/*keyword*/ (
      <<a>>/*parameter*/,
      <<b>>/*parameter*/,
    ) <<==>>/*operator*/ (<<1>>/*number*/, <<2>>/*number*/)
    (
      <<c>>/*variable,readonly*/,
      <<d>>/*variable,readonly*/,
    ) <<<->>/*operator*/ <<List>>/*variable,readonly*/((<<a>>/*parameter*/, <<b>>/*parameter*/))
    <<if>>/*keyword*/ (
      <<a>>/*parameter*/,
      <<b>>/*parameter*/,
      <<c>>/*variable,readonly*/,
      <<d>>/*variable,readonly*/,
    ) <<==>>/*operator*/ (<<1>>/*number*/, <<2>>/*number*/, <<3>>/*number*/, <<4>>/*number*/)
    <<e>>/*variable,readonly*/ <<=>>/*operator*/ (
      <<a>>/*parameter*/,
      <<b>>/*parameter*/,
      <<c>>/*variable,readonly*/,
      <<d>>/*variable,readonly*/,
    )
    <<if>>/*keyword*/ <<e>>/*variable,readonly*/ <<==>>/*operator*/ (<<1>>/*number*/, <<2>>/*number*/, <<3>>/*number*/, <<4>>/*number*/)
    <<f>>/*parameter*/ <<<->>/*operator*/ <<List>>/*variable,readonly*/(<<e>>/*variable,readonly*/)
  } <<yield>>/*keyword*/ {
    (
      <<a>>/*parameter*/,
      <<b>>/*parameter*/,
      <<c>>/*variable,readonly*/,
      <<d>>/*variable,readonly*/,
      <<e>>/*variable,readonly*/,
      <<f>>/*parameter*/,
    )
  }

}