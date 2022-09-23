package tests.pc

import tests.BaseCompletionSuite

class CompletionSbtLibSuite extends BaseCompletionSuite {
  check(
    "simple",
    """|val dependency = "io.circe" %% "circe-co@@"
       |""".stripMargin,
    """|
       |""".stripMargin,
    filename = "A.sbt",
  )

  check(
      "artifact",
      """|val dependency = "io.circe" %% "circe-co@@"
         |""".stripMargin,
      """|
         |""".stripMargin,
      filename = "A.sbt",
    )
}
