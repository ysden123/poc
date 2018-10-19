/*
 * Copyright (c) 2018. Yuriy Stul
 */

package com.stulsoft.poc.json.json4s

import org.json4s._
import org.json4s.jackson.JsonMethods.parse

import scala.io.Source

/**
  * @author Yuriy Stul
  */
object ParseArrayWithExtract extends App {
  implicit val formats: DefaultFormats = DefaultFormats
  println("==>ParseArrayWithExtract")
  try {
    val jsonObject = parse(Source.fromResource("arrayOfObjects.json").getLines().mkString)
    val items: Seq[TheObject] = jsonObject.children.map(child => child.extract[TheObject])
    println(s"items: $items")
  }
  catch {
    case e: Exception => println(s"sError: ${e.getMessage}")
  }
}
