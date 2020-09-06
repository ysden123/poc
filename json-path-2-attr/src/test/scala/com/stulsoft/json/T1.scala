/*
 * Copyright (c) 2020. Yuriy Stul
 */

package com.stulsoft.json

import com.typesafe.scalalogging.StrictLogging

import scala.collection.mutable.ArrayBuffer

/**
 * @author Yuriy Stul
 */
object T1  extends App with StrictLogging {

//  test2()
//  test3()
  test4()

  def test2(): Unit = {
    logger.info("==>test2")
    val paths = ArrayBuffer.empty[(String, Any)]
    PathBuilder.buildPath(Utils.jsonMapFromResource("test2.json"), "", paths)
    logger.debug("paths: {}", paths)
    logger.debug(" ")
    logger.debug("for d: {}",PathBuilder.findPathToAttr(paths, "d"))
    logger.debug(" ")
    logger.debug("for a: {}",PathBuilder.findPathToAttr(paths, "a"))
  }

  def test3(): Unit = {
    logger.info("==>test3")
    val paths = ArrayBuffer.empty[(String, Any)]
    PathBuilder.buildPath(Utils.jsonMapFromResource("test3.json"), "", paths)
    logger.debug("paths: {}", paths)
    logger.debug(" ")
    logger.debug("for d: {}",PathBuilder.findPathToAttr(paths, "d"))
    logger.debug(" ")
    logger.debug("for a: {}",PathBuilder.findPathToAttr(paths, "a"))
  }

  def test4(): Unit = {
    logger.info("==>test4")
    val paths = ArrayBuffer.empty[(String, Any)]
    PathBuilder.buildPath(Utils.jsonMapFromResource("test4.json"), "", paths)
    logger.debug("paths: {}", paths)
    logger.debug(" ")
    logger.debug("for a: {}",PathBuilder.findPathToAttr(paths, "a"))
  }

}
