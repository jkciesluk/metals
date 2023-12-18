package scala.meta.internal.builds

import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.StandardCopyOption

import scala.meta.io.AbsolutePath

trait BuildTool {

  def digest(workspace: AbsolutePath): Option[String]

  protected lazy val tempDir: Path = {
    val dir = Files.createTempDirectory("metals")
    dir.toFile.deleteOnExit()
    dir
  }

  def redirectErrorOutput: Boolean = false

  def executableName: String

  def projectRoot: AbsolutePath

  val forcesBuildServer = false

  val isBloopInstallProvider = false

}

object BuildTool {

  def copyFromResource(
      tempDir: Path,
      filePath: String,
      destination: Option[String] = None,
  ): Path = {
    val embeddedFile =
      this.getClass.getResourceAsStream(s"/$filePath")
    val outFile = tempDir.resolve(destination.getOrElse(filePath))
    Files.createDirectories(outFile.getParent)
    Files.copy(embeddedFile, outFile, StandardCopyOption.REPLACE_EXISTING)
    outFile
  }

  trait Verified
  case class IncompatibleVersion(buildTool: VersionRecommendation)
      extends Verified {
    def message: String = s"Unsupported $buildTool version ${buildTool.version}"
  }
  case class NoChecksum(buildTool: BuildTool, root: AbsolutePath)
      extends Verified {
    def message: String =
      s"Could not calculate checksum for ${buildTool.executableName} in $root"
  }
  case class Found(buildTool: BuildTool, digest: String) extends Verified

}
