/*
 * Copyright (c) 2018. Yuriy Stul
 */

package com.stulsoft.poc.json.json4s

import org.json4s._
import org.json4s.jackson.JsonMethods._

/** Parses JSON using ''extract''
  *
  * @author Yuriy Stul
  */
object ParserWithExtract extends App {
  println("==>ParserWithExtract")
  implicit val formats: DefaultFormats = DefaultFormats
  val jsonObject = parse("""{"name":"some name", "age":123, "height":123.45}""")
  println(jsonObject)
  val theObject = jsonObject.extract[TheObject]
  println(theObject)
}

