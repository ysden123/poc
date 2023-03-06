/*
 * Copyright (c) 2019. Yuriy Stul
 */

package com.stulsoft.poc.json.json4s

import org.json4s.*
import org.json4s.JsonAST.{JArray, JField, JInt, JObject, JString, JValue}
import org.json4s.jackson.JsonMethods.parse

import scala.io.Source

/** Finds a JSON objects with same value of <i>name</i> field
 *
 * @author Yuriy Stul
 */
object FindDuplicates4 {
  given formats: DefaultFormats = DefaultFormats

  def findDuplicates(path: String): Unit = {
    println(s"Looking in $path")
    try {
      val jsonObject = parse(Source.fromResource(path).getLines().mkString)
      val result = for {
        JObject(allChildren) <- jsonObject
        JField("items", items) <- allChildren
        JObject(item) <- items
        JField("name", JString(name)) <- item
        JField("age", JInt(age)) <- item
      } yield (name, age)

      result.foreach { case (name: String, age: BigInt) => println(s"name: $name, age = $age") }
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