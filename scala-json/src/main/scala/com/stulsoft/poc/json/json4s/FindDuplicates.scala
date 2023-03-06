/*
 * Copyright (c) 2019. Yuriy Stul
 */

package com.stulsoft.poc.json.json4s

import org.json4s.*
import org.json4s.JsonAST.{JField, JObject, JString}
import org.json4s.jackson.JsonMethods.parse

import scala.io.Source

/** Finds a JSON objects with same value of <i>name</i> field
 *
 * @author Yuriy Stul
 */
object FindDuplicates {
  given formats: DefaultFormats = DefaultFormats

  def findDuplicates(path: String): Unit = {
    println(s"Looking in $path")
    try {
      val jsonObject = parse(Source.fromResource(path).getLines().mkString)
      val names = for {
        JObject(child) <- jsonObject
        JField("name", JString(name)) <- child
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

  def main(args: Array[String]): Unit = {
    println("==>FindDuplicates")
    findDuplicates("arrayOfObjects.json")
    findDuplicates("arrayOfObjects2.json")

  }
}
