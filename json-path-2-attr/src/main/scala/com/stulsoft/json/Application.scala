/*
 * Copyright (c) 2020. Yuriy Stul
 */

package com.stulsoft.json

import com.typesafe.scalalogging.StrictLogging

import scala.collection.mutable.ArrayBuffer

/**
 * @author Yuriy Stul
 */
object Application extends App with StrictLogging {
  logger.info("==>Application")

  test2()
  test3()

  def test2(): Unit = {
    logger.info("==>test2")
    val paths = ArrayBuffer.empty[(String, Any)]
    PathBuilder.buildPath(Utils.jsonMapFromResourse("test2.json"), "", paths)
    logger.debug("paths: {}", paths)
    logger.debug(" ")
    logger.debug("for d: {}",PathBuilder.findPathToAttr(paths, "d"))
    logger.debug(" ")
    logger.debug("for a: {}",PathBuilder.findPathToAttr(paths, "a"))
  }

  def test3(): Unit = {
    logger.info("==>test3")
    val paths = ArrayBuffer.empty[(String, Any)]
    PathBuilder.buildPath(Utils.jsonMapFromResourse("test3.json"), "", paths)
    logger.debug("paths: {}", paths)
    logger.debug(" ")
    logger.debug("for d: {}",PathBuilder.findPathToAttr(paths, "d"))
    logger.debug(" ")
    logger.debug("for a: {}",PathBuilder.findPathToAttr(paths, "a"))
  }

}
