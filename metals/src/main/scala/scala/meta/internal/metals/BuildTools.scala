package scala.meta.internal.metals

import java.nio.file.Files
import scala.meta.io.AbsolutePath
import MetalsEnrichments._
import java.util.Properties

/**
 * Detects what build tool is used in this workspace.
 *
 * Although we only support a limited set of build tools, knowing
 * what build tool is used in the workspace helps to produce better error
 * for people using unsupported build tools. For example: "Gradle is not supported"
 * instead of "Unsupported build tool".
 */
final class BuildTools(workspace: AbsolutePath) {
  // Naive implementations to detect which build tool is being used,
  // but can be improved with any custom logic.
  def isBloop: Boolean = {
    workspace.resolve(".bloop").isDirectory && {
      val ls = Files.list(workspace.resolve(".bloop").toNIO)
      val hasJsonFile =
        ls.iterator().asScala.exists(_.getFileName.toString.endsWith(".json"))
      ls.close()
      hasJsonFile
    }
  }
  // Returns true if there's a build.sbt file or project/build.properties with sbt.version
  def isSbt: Boolean = {
    workspace.resolve("build.sbt").isFile || {
      val buildProperties =
        workspace.resolve("project").resolve("build.properties")
      buildProperties.isFile && {
        val props = new Properties()
        val in = Files.newInputStream(buildProperties.toNIO)
        try props.load(in)
        finally in.close()
        props.getProperty("sbt.version") != null
      }
    }
  }
  def isMill: Boolean = workspace.resolve("build.sc").isFile
  def isGradle: Boolean = workspace.resolve("build.gradle").isFile
  def isMaven: Boolean = workspace.resolve("pom.xml").isFile
  def isPants: Boolean = workspace.resolve("pants.ini").isFile
  def isBazel: Boolean = workspace.resolve("WORKSPACE").isFile
  import BuildTool._
  def asSbt: Option[Sbt] =
    if (isSbt) Some(SbtVersion(workspace))
    else None
  def all: List[BuildTool] = {
    val buf = List.newBuilder[BuildTool]
    if (isBloop) buf += Bloop
    buf ++= asSbt.toList
    if (isMill) buf += Mill
    if (isGradle) buf += Gradle
    if (isMaven) buf += Maven
    if (isPants) buf += Pants
    if (isBazel) buf += Bazel
    buf.result()
  }
  override def toString: String = {
    val names = all.mkString("+")
    if (names.isEmpty) "<no build tool>"
    else names
  }
}
