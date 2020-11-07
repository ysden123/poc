/*
 * Copyright (c) 2020. StulSoft
 */

package com.stulsoft.poc.json.jackson

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.node.ArrayNode
import com.typesafe.scalalogging.StrictLogging

import scala.io.Source


/**
 * @author Yuriy Stul
 */
object ParserEx1 extends App with StrictLogging {
  test1()
  test2()

  def test1(): Unit = {
    logger.info("==>test1")

    // Create Reader/InputStream/File
    //    val file = new Nothing("post.json")
    val s = Source.fromResource("arrayOfObjects.json")
    val jsonText = s.getLines().mkString
    s.close()

    val mapper = new ObjectMapper()
    val jsonArrayNode = mapper.readTree(jsonText).asInstanceOf[ArrayNode]
    println(s"jsonArrayNode: ${jsonArrayNode.toString}")

    jsonArrayNode.forEach(jsonNode => println(s"jsonNode: $jsonNode"))
  }

  def test2(): Unit ={
    logger.info("==>test2")
    val s = Source.fromResource("arrayOfObjects.json")
    val jsonText = s.getLines().mkString
    s.close()

    val mapper = new ObjectMapper()
    val jsonArrayNode = mapper.readTree(jsonText).asInstanceOf[ArrayNode]

    jsonArrayNode.forEach(node=>{
      val name=node.get("name").asText()
      val age=node.get("age").asInt()
      val height=node.get("height").asDouble()
      println(s"name: $name, age = $age, height = $height")
    })
  }
}
