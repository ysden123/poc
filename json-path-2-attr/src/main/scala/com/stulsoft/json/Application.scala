/*
 * Copyright (c) 2020. Yuriy Stul
 */

package com.stulsoft.json

import com.typesafe.scalalogging.StrictLogging

import scala.collection.mutable.ArrayBuffer
import scala.io.StdIn

/**
 * @author Yuriy Stul
 */
object Application extends App with StrictLogging {
  try {
    println("Enter path to file with JSON:")
    val filePath = StdIn.readLine()
//    val filePath = """c:\Users\yuriy.s\Documents\Webpals\Diggy\ETL bulk edit account config\jsons\research2\testBody.json"""
    println("Enter attribute name:")
    val attrName = StdIn.readLine()
//    val attrName = "status"
//    val attrName = "action"
    val paths = ArrayBuffer.empty[(String, Any)]
    PathBuilder.buildPath(Utils.jsonMapFromFile(filePath), "", paths)
    logger.info("for {}: {}", attrName, PathBuilder.findPathToAttr(paths, attrName))
  } catch {
    case x: Exception => x.printStackTrace()
  }
}
