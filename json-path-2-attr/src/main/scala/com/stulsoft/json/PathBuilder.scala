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

  private def path(parent: String, n: String): String = {
    parent match {
      case "" => n
      case x => x + "." + n
    }
  }

  def buildPath(m: Map[String, Any], parent: String, paths: ArrayBuffer[(String, Any)]): Unit = {
    m.foreach { case (n, v) => v match {
      case m: Map[String, Any] =>
        buildPath(m,
          path(parent, n),
          paths)
      case l: List[Any] =>
        val thePath = path(parent, n)
        l.foreach(_ => {
          paths.addOne(thePath -> v)
        })
      case l: List[Map[String, Any]] =>
        buildPath(l,
          path(parent, n),
          paths)
      case _ =>
        //        logger.debug("path: {}, n: {}, v: {}", parent, n, v)
        val thePath = path(parent, n)
        paths.addOne(thePath -> v)
    }
    }
  }

  def buildPath(l: List[Map[String, Any]], parent: String, paths: ArrayBuffer[(String, Any)]): Unit = {
    l.foreach(m => m.foreach { case (n, v) => v match {
      case m: Map[String, Any] =>
        buildPath(m,
          path(parent, n),
          paths)
      case l: List[Map[String, Any]] =>
        buildPath(l,
          path(parent, n),
          paths)
      case _ =>
        //        logger.debug("path: {}, n: {}, v: {}", parent, n, v)
        val thePath = path(parent, n)
        paths.addOne(thePath -> v)
    }
    })
  }

  def findPathToAttr(paths: Iterable[(String, Any)], attr: String): Iterable[(String, Any)] = {
    paths
      .filter(p => p._1.equals(attr) || p._1.endsWith("." + attr))
  }
}
