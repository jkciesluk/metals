package scala.meta.internal.bsp

import scala.meta.internal.metals.BloopServers

import ch.epfl.scala.bsp4j.BspConnectionDetails

/**
 * The resolved BSP Connection details from a workspace. This can either exists
 * or not, and also distinguises between multiple resolved .bsp/<entries> and a
 * single resolved entry such as .bsp/sbt.json
 */
sealed trait BspResolvedResult extends Product with Serializable
case object ResolvedNone extends BspResolvedResult
case object ResolvedBloop extends BspResolvedResult

/** WORKSPACE file is found, but bsp connection not found. */
case class ResolvedBspOne(details: BspConnectionDetails)
    extends BspResolvedResult
case class ResolvedMultiple(md5: String, details: List[BspConnectionDetails])
    extends BspResolvedResult

object BspResolvedResult {
  def fromDetails(details: BspConnectionDetails): BspResolvedResult =
    details.getName() match {
      case BloopServers.name => ResolvedBloop
      case _ => ResolvedBspOne(details)
    }
}
