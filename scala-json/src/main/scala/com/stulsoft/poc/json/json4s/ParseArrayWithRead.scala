/*
 * Copyright (c) 2018. Yuriy Stul
 */

package com.stulsoft.poc.json.json4s

import org.json4s._
import org.json4s.jackson.Serialization.read

import scala.io.Source

/**
  * @author Yuriy Stul
  */
object ParseArrayWithRead extends App {
  println("==>ParseArrayWithRead")
  try {
    implicit val formats = DefaultFormats
    val items = read[Seq[TheObject]](Source.fromResource("arrayOfObjects.json").getLines().mkString)
    items.foreach(println)
    val totalAge = items.map(i => i.age).sum
    val totalHeight = items.map(i => i.height).sum
    println(s"Total age=$totalAge, total height=$totalHeight")
  }
  catch {
    case e: Exception => println(s"sError: ${e.getMessage}")
  }
}
