/*
 * Copyright (c) 2023. StulSoft
 */

package com.stulsoft.poc.json.json4s

import org.json4s._
import org.json4s.jackson.Serialization.read
import org.json4s.jackson.Serialization.write
import org.json4s.FieldSerializer._

object UsingRename:

  private def writeTest(): Unit =
    println("==>writeTest")
    try {
      val rename = FieldSerializer[TheObject4Rename](renameTo("theObject", "object"),
        renameFrom("object", "theObject"))

      given format: Formats = DefaultFormats + rename

      val theObject4Rename = TheObject4Rename(123, "some value")
      val json = write(theObject4Rename)
      println(s"json: $json")
    } catch
      case exception: Exception => exception.printStackTrace()

  private def readTest(): Unit =
    println("==>readTest")
    try {
      val rename = FieldSerializer[TheObject4Rename](renameTo("theObject", "object"),
        renameFrom("object", "theObject"))

      given format: Formats = DefaultFormats + rename

      val theObject4Rename = read[TheObject4Rename](StreamInput(getClass.getClassLoader.getResourceAsStream("forRename.json")))
      println(s"theObject4Rename: $theObject4Rename")
    } catch
      case exception: Exception => exception.printStackTrace()

  def main(args: Array[String]): Unit = {
    println("==>main")
    writeTest()
    readTest()
  }
