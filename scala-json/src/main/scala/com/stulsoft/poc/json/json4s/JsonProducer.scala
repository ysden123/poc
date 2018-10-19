/*
 * Copyright (c) 2018. Yuriy Stul
 */

package com.stulsoft.poc.json.json4s

import org.json4s.JsonDSL._
import org.json4s.jackson.JsonMethods._

/** Producing JSON
  *
  * See [[http://json4s.org/]]
  *
  * @author Yuriy Stul
  */
object JsonProducer extends App {
  val jsonList = compact(render(List(1, 2, 3)))
  println(s"jsonList: $jsonList")

  val jsonTuple = compact(render("name" -> "joe"))
  println(s"jsonTuple: $jsonTuple")

  // operator produces object by combining fields.
  val jsonCombined = compact(render(("name" -> "joe") ~ ("age" -> 35)))
  println(s"jsonCombined: $jsonCombined")
}
