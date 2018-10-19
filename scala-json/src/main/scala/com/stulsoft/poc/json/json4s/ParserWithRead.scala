/*
 * Copyright (c) 2018. Yuriy Stul
 */

package com.stulsoft.poc.json.json4s

import org.json4s._
import org.json4s.jackson.Serialization.read

/**
  * @author Yuriy Stul
  */
object ParserWithRead extends App {
  println("==>ParserWithRead")
  implicit val formats: DefaultFormats = DefaultFormats
  val theObject = read[TheObject]("""{"name":"some name", "age":123, "height":123.45}""")
  println(theObject)
}
