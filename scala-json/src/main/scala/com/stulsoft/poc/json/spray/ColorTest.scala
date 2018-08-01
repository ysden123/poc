/*
 * Copyright (c) 2018. Yuriy Stul
 */

package com.stulsoft.poc.json.spray

import com.stulsoft.poc.json.spray.MyJsonProtocol._
import spray.json._

/** Usage of Color
  *
  * @author Yuriy Stul
  */
object ColorTest extends App {
  val json = Color("CadetBlue", 95, 158, 160).toJson
  println(s"json: $json")

  val color = json.convertTo[Color]
  println(s"color: $color")

  val jsonArray=Array(Color("CadetBlue", 95, 158, 160), Color("Green", 0, 255, 0)).toJson
  println(s"jsonArray: $jsonArray")

  val colorArray=jsonArray.convertTo[Array[Color]]
  println(s"""colorArray: [${colorArray.mkString(", ")}]""")
}
