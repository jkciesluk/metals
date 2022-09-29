package scala.meta.internal.mtags

import scala.collection.JavaConverters._
import scala.concurrent.Await
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.concurrent.duration._
import scala.util.matching.Regex

import scala.meta.internal.tokenizers.Chars

import coursierapi.Complete

object CoursierComplete {
  lazy val api: Complete = coursierapi.Complete
    .create()
    .withScalaVersion(scalaVersion)
    .withScalaBinaryVersion(
      if (scalaVersion.startsWith("3")) "3"
      else scalaVersion.split('.').take(2).mkString(".")
    )
  lazy val scalaVersion = BuildInfo.scalaCompilerVersion
  def complete(
      dependency: String,
      includeScala: Boolean = true
  ): List[String] = {

    def completions(s: String): List[String] = {
      val futureCompletions = Future {
        api.withInput(s).complete().getCompletions().asScala.toList
      }
      try Await.result(futureCompletions, 10.seconds)
      catch {
        case _: Throwable => Nil
      }
    }

    val javaCompletions = completions(dependency)
    val scalaCompletions =
      if (
        includeScala &&
        dependency.endsWith(":") && dependency.count(_ == ':') == 1
      )
        completions(dependency + ":").map(":" + _)
      else Nil
    javaCompletions ++ scalaCompletions
  }
  def inferEditRange(point: Int, text: String): (Int, Int) = {
    val editStart = {
      var i = point - 1
      while (
        i >= 0 && {
          val c = text.charAt(i)
          (Chars.isIdentifierPart(c) || c == '.' || c == '-')
        }
      ) { i -= 1 }
      i + 1
    }
    val editEnd = {
      var i = point
      val textLen = text.length()
      while (
        i < textLen && {
          val c = text.charAt(i)
          (Chars.isIdentifierPart(c) || c == '.' || c == '-')
        }
      ) {
        i += 1
      }
      i
    }
    (editStart, editEnd)
  }

  val reg: Regex = """//>\s*using\s+libs?\s+"?(.*)""".r
  def isScalaCliDep(line: String): Option[String] = {
    line match {
      case reg(deps) =>
        val dep =
          deps.split(",").last.trim().stripPrefix("\"").stripSuffix("\"")
        Some(dep)
      case _ =>
        None
    }
  }
}