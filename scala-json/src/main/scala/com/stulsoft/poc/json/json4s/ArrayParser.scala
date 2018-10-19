/*
 * Copyright (c) 2018. Yuriy Stul
 */

package com.stulsoft.poc.json.json4s

import org.json4s._
import org.json4s.jackson.JsonMethods._

import scala.io.Source

/** Parses JSON with array of objects
  *
  * @author Yuriy Stul
  */
object ArrayParser extends App {
  try {
    val jsonObject = parse(Source.fromResource("arrayOfObjects.json").getLines().mkString)
    val items: Seq[(String, BigInt, Double)] = for {
      JArray(listChild) <- jsonObject
      JObject(child) <- listChild
      JField("name", JString(name)) <- child
      JField("age", JInt(age)) <- child
      JField("height", JDouble(height)) <- child
    } yield (name, age, height)
    items.foreach(item => println(s"name: ${item._1}, age=${item._2}, height=${item._3}"))
    val totalAge = items.map(item => item._2).sum
    val totalHeight = items.map(item => item._3).sum
    println(s"Total age=$totalAge, total height=$totalHeight")
  }
  catch {
    case e: Exception => println(s"sError: ${e.getMessage}")
  }
}
