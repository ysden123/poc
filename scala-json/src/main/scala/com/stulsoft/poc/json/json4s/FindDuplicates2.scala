/*
 * Copyright (c) 2019. Yuriy Stul
 */

package com.stulsoft.poc.json.json4s

import org.json4s.DefaultFormats
import org.json4s.JsonAST.{JArray, JField, JObject, JString}
import org.json4s.jackson.JsonMethods.parse

import scala.io.Source

/** Finds a JSON objects with same value of <i>name</i> field
 *
 * @author Yuriy Stul
 */
object FindDuplicates2 extends App {
  implicit val formats: DefaultFormats = DefaultFormats
  println("==>FindDuplicates2")
  findDuplicates("arrayOfObjects3.json")

  def findDuplicates(path: String): Unit = {
    println(s"Looking in $path")
    try {
      val jsonObject = parse(Source.fromResource(path).getLines().mkString)
      val names = for {
        JObject(child) <- jsonObject
        JField("items", items) <- child
        JObject(item) <- items
        JField("name", JString(name)) <- item
      } yield name
      val duplicates = names.groupBy(identity).collect { case (x, List(_, _, _*)) => x }
      if (duplicates.isEmpty)
        println("Do duplicates were found")
      else
        println(s"Duplicates: $duplicates")
    }
    catch {
      case e: Exception => println(s"sError: ${e.getMessage}")
    }
  }
}
