/*
 * Copyright (c) 2020. Yuriy Stul
 */

package com.stulsoft.json

import org.json4s.jackson.JsonMethods.parse

import scala.io.Source

/**
 * @author Yuriy Stul
 */
object Utils {
  def jsonMapFromResource(path: String): Map[String, Any] = {
    implicit val formats = org.json4s.DefaultFormats
    val r = Source.fromResource(path)
    val jsonStr = r.getLines().mkString
    parse(jsonStr).extract[Map[String, Any]]
  }

  def jsonMapFromFile(path: String): Map[String, Any] = {
    implicit val formats = org.json4s.DefaultFormats
    val r = Source.fromFile(path)
    val jsonStr = r.getLines().mkString
    parse(jsonStr).extract[Map[String, Any]]
  }
}
