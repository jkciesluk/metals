package tests

import scala.meta.internal.metals.{BuildInfo => V}
import scala.meta.internal.metals.CompilerOffsetParams
import scala.meta.internal.metals.EmptyCancelToken
import scala.meta.internal.metals.MetalsEnrichments._
import scala.meta.internal.pc.ScalaPresentationCompiler

abstract class SemanticTokensExpectSuite(
    directoryName: String,
    inputProperties: => InputProperties,
    scalaVersion: String,
) extends DirectoryExpectSuite(directoryName) {

  override lazy val input: InputProperties = inputProperties
  private val compiler = new ScalaPresentationCompiler()

  override def testCases(): List[ExpectTestCase] = {
    input.scalaFiles.map { file =>
      ExpectTestCase(
        file,
        () => {
          val tokens = compiler
            .semanticTokens(
              CompilerOffsetParams(
                file.file.toURI,
                file.code,
                0,
                EmptyCancelToken,
              )
            )
            .get()
          TestSemanticTokens.semanticString(
            file.code,
            tokens.asScala.toList.map(_.toInt),
          )
        },
      )
    }
  }

  override def afterAll(): Unit = {
    compiler.shutdown()
  }
}

class SemanticTokensExpectScala2Suite
    extends SemanticTokensExpectSuite(
      "semanticTokens",
      InputProperties.scala2(),
      V.scala213,
    )

class SemanticTokensExpectScala3Suite
    extends SemanticTokensExpectSuite(
      "semanticTokens-scala3",
      InputProperties.scala3(),
      V.scala3,
      ScalaPresentationCompiler(),
    )
