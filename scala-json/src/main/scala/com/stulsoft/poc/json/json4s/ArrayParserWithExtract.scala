/*
 * Copyright (c) 2018. Yuriy Stul
 */

package com.stulsoft.poc.json.json4s

import org.json4s._
import org.json4s.jackson.JsonMethods.parse

import scala.io.Source

/** Parses JSON with array of objects using ''extract''
  *
  * @author Yuriy Stul
  */
object ArrayParserWithExtract extends App {
  implicit val formats: DefaultFormats = DefaultFormats
  println("==>ArrayParserWithExtract")
  try {
    val jsonObject = parse(Source.fromResource("arrayOfObjects.json").getLines().mkString)
    val items: Seq[TheObject] = jsonObject.children.map(child => child.extract[TheObject])
    println(s"items: $items")
  }
  catch {
    case e: Exception => println(s"sError: ${e.getMessage}")
  }
}
