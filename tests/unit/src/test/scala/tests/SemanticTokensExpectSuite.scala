package tests

import scala.meta.internal.metals.CompilerOffsetParams
import scala.meta.internal.metals.EmptyCancelToken
import scala.meta.internal.metals.MetalsEnrichments._
import scala.meta.internal.pc.ScalaPresentationCompiler

class SemanticTokensExpectSuite extends DirectoryExpectSuite("semanticTokens") {

  override lazy val input: InputProperties = InputProperties.scala2()
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
