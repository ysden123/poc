/*
 * Copyright (c) 2018. Yuriy Stul
 */

package com.stulsoft.pconfig

import com.typesafe.config.{ConfigFactory, ConfigRenderOptions}
import io.vertx.core.json.JsonObject

/** The use of the Typesafe configuration as source for Vertx configuration (JsonObject).
  *
  * @author Yuriy Stul
  */
object ToJson extends App {
  println("==>ToJson")
  val conf = ConfigFactory.load()
  val forJson = conf.getConfig("forJson").root().render(ConfigRenderOptions.concise())
  //  println(forJson)

  val jsonConfig = new JsonObject(forJson.toString)
  println(jsonConfig)
  println(s"item1 (Integer) = ${jsonConfig.getJsonObject("part1").getInteger("item1")}")
  println(s"item2 (String) = ${jsonConfig.getJsonObject("part1").getString("item2")}")
  println(s"items1 [String] = ${jsonConfig.getJsonObject("part1").getJsonArray("items1").toString}")
  println(s"item3 (String) = ${jsonConfig.getJsonObject("part1").getJsonObject("part2").getString("item3")}")

  println("<==ToJson")
}
