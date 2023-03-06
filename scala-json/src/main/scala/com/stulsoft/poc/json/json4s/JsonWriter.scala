/*
 * Copyright (c) 2018. Yuriy Stul
 */

package com.stulsoft.poc.json.json4s

import org.json4s.*
import org.json4s.jackson.Serialization.*

/** Converts an object or objects to JSON string.
 *
 * @author Yuriy Stul
 */
object JsonWriter {
  implicit val formats: DefaultFormats = DefaultFormats

  private val theObjects = List(
    TheObject("my name 1", 123, 432.09),
    TheObject("my name 2", 56, 4985),
    TheObject("my name 3", 78, 543),
    TheObject("my name 4", 321, 123.456)
  )

  def main(args: Array[String]): Unit = {
    println("==>JsonWriter")
    val theObject = TheObject("my name", 123, 432.09)
    val json = write(theObject)
    println(s"(1) json: $json")

    val json2 = write(theObjects)
    println(s"(2) json: $json2")
    val json3 = writePretty(theObjects)
    println(s"(3) json: $json3")

    val theObjectWithOption = TheObjectWithOption("name 2", 456, 1.0, Option.empty)
    println(s"(4) json with option: ${write(theObjectWithOption)}")

    val theObjectWithOption2 = TheObjectWithOption("name 2", 456, 1.0, Option("the option value"))
    println(s"(5) json with option: ${write(theObjectWithOption2)}")

  }
}
