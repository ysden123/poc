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

  val price2 = for {
    JObject(child) <- jsonObject2
    JField("price", JDecimal(price)) <- child
  } yield price
  println(s"price2=$price2")

  val jsonObject3 = parse("""{"name": "Toy", "price": 35.35}""")
  println(s"jsonObject3: $jsonObject3")

  val price3 = for {
    JObject(child) <- jsonObject3
    JField("price", JDouble(price)) <- child
  } yield price
  println(s"price2=$price3")
}
