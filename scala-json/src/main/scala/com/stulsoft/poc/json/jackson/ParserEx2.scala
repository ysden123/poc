/*
 * Copyright (c) 2020. StulSoft
 */

package com.stulsoft.poc.json.jackson

import com.fasterxml.jackson.databind.{JsonNode, ObjectMapper}
import com.fasterxml.jackson.databind.node.ArrayNode
import com.typesafe.scalalogging.StrictLogging

import scala.io.Source

case class MyObject(jsonNode: JsonNode) {
  lazy val name: String = jsonNode.get("name").asText()
  lazy val age: Int = jsonNode.get("age").asInt()
  lazy val height: Double = jsonNode.get("height").asDouble()
}

/**
 * @author Yuriy Stul
 */
object ParserEx2 extends App with StrictLogging {
  test1()
  test2()

  def test1(): Unit = {
    logger.info("==>test1")
    val s = Source.fromResource("arrayOfObjects.json")
    val jsonText = s.getLines().mkString
    s.close()

    val mapper = new ObjectMapper()
    val jsonArrayNode = mapper.readTree(jsonText).asInstanceOf[ArrayNode]

    jsonArrayNode.forEach(node => {
      val myObject=MyObject(node)
      println(s"name: ${myObject.name}, age = ${myObject.age}, height = ${myObject.height}")
    })
  }

  def test2(): Unit = {
    logger.info("==>test2")
    val s = Source.fromResource("arrayOfObjects.json")
    val jsonText = s.getLines().mkString
    s.close()

    val mapper = new ObjectMapper()
    val jsonArrayNode = mapper.readTree(jsonText).asInstanceOf[ArrayNode]

    jsonArrayNode.forEach(node => {
      val myObject=MyObject(node)
      println(s"myObject: $myObject")
    })
  }
}
