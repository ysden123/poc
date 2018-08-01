/*
 * Copyright (c) 2018. Yuriy Stul
 */

package com.stulsoft.poc.json.spray

import spray.json.{DefaultJsonProtocol, RootJsonFormat}

/** With protocol
  * See [[https://github.com/spray/spray-json Usage]]
  *
  * @author Yuriy Stul
  */
case class Color(name: String, red: Int, green: Int, blue: Int)

object MyJsonProtocol extends DefaultJsonProtocol {
  implicit val colorFormat: RootJsonFormat[Color] = jsonFormat4(Color) // 4 here is number of parameters in Color constructor
}