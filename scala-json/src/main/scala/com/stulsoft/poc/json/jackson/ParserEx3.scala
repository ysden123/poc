/*
 * Copyright (c) 2020. StulSoft
 */

package com.stulsoft.poc.json.jackson

import com.fasterxml.jackson.databind.{JsonNode, ObjectMapper}
import com.fasterxml.jackson.databind.node.ArrayNode
import com.typesafe.scalalogging.StrictLogging

import scala.io.Source

/**
 * @author Yuriy Stul
 */
object ParserEx3 extends App with StrictLogging {
  try {
    val s = Source.fromResource("candleLightExample.json")
    val jsonText = s.getLines().mkString
    s.close()
    val mapper = new ObjectMapper()
    val jsonNode = mapper.readTree(jsonText)
    println(s"jsonNode.isArray = ${jsonNode.isArray}")
    println(s"jsonNode.isObject = ${jsonNode.isObject}")

    val items=jsonNode.get("items").asInstanceOf[ArrayNode]
    println(s"size=${items.size()}")
    (0 until items.size())
      .map(i=>items.get(i))
      .filter(node => "candles" == node.get("category").asText)
      .foreach(node => println(node))

  } catch {
    case ex: Exception =>
      logger.error(ex.getMessage, ex)
  }
}
