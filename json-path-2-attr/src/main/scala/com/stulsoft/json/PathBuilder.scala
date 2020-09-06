/*
 * Copyright (c) 2020. Yuriy Stul
 */

package com.stulsoft.json

import com.typesafe.scalalogging.StrictLogging

import scala.collection.Map
import scala.collection.mutable.ArrayBuffer

/**
 * @author Yuriy Stul
 */
object PathBuilder extends StrictLogging {

  def buildPath(m: Map[String, Any], parent: String, paths: ArrayBuffer[(String, Any)]): Unit = {
    m.foreach { case (n, v) => v match {
      case m: Map[String, Any] =>
        buildPath(m,
          parent match {
            case "" => n
            case x => x + "." + n
          },
          paths)
      case _ =>
        //        logger.debug("path: {}, n: {}, v: {}", parent, n, v)
        val path = parent match {
          case "" => n
          case x => x + "." + n
        }
        paths.addOne(path -> v)
    }
    }
  }

  def findPathToAttr(paths: Iterable[(String, Any)], attr: String): Iterable[(String, Any)] = {
    paths
      .filter { case (n: String, v: Any) => n.equals(attr) || n.endsWith("." + attr) }
  }
}
