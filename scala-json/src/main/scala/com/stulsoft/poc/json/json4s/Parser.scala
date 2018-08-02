/*
 * Copyright (c) 2018. Yuriy Stul
 */

package com.stulsoft.poc.json.json4s

import org.json4s._
import org.json4s.jackson.JsonMethods._

/** Parsing JSON
  *
  * See [[http://json4s.org/]]
  *
  * @author Yuriy Stul
  */
object Parser extends App {
  val jsonObject1 = parse("""{"numbers":[1, 2, 3, 4]}""")
  println(s"jsonObject1: $jsonObject1")

  val jsonObject2 = parse("""{"name": "Toy", "price": 35.35}""", useBigDecimalForDouble = true)
  println(s"jsonObject2: $jsonObject2")
}
