/*
 * Copyright (c) 2019. Yuriy Stul
 */

package com.stulsoft.poc.json.json4s

import org.json4s.DefaultFormats
import org.json4s.JsonAST.{JField, JObject, JString}
import org.json4s.jackson.JsonMethods.parse
import org.json4s.*

import scala.io.Source

/** Finds a JSON objects with same value of <i>name</i> field
 *
 * @author Yuriy Stul
 */
object FindDuplicates3 {
  given formats: DefaultFormats = DefaultFormats

  def findDuplicates(path: String): Unit = {
    println(s"Looking in $path")
    try {
      val jsonObject = parse(Source.fromResource(path).getLines().mkString)
      val items = jsonObject \ "items"
      val names = items \ "name"
      val duplicates = (names
        .children
        .groupBy(identity)
        .collect { case (x, List(_, _, _*)) => x })
        .asInstanceOf[List[JString]]
        .map(n => n.s)

      if (duplicates.isEmpty)
        println("Do duplicates were found")
      else {
        println(s"Duplicates:")
        duplicates.foreach(println)
      }

      //      duplicates.asInstanceOf[List[JString]].foreach(i=>println(i.s))
    }
    catch {
      case e: Exception => println(s"sError: ${e.getMessage}")
    }
  }

  def main(args: Array[String]): Unit = {
    println("==>FindDuplicates2")
    findDuplicates("arrayOfObjects3.json")
  }
}
