/*
 * Copyright (c) 2020. StulSoft
 */

package com.stulsoft.poc.json.jacksonscala

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import com.typesafe.scalalogging.StrictLogging

import scala.io.Source

/**
 * @author Yuriy Stul
 */
object ParserEx1 extends App with StrictLogging{
  val mapper = new ObjectMapper()
  mapper.registerModule(DefaultScalaModule)
  val s = Source.fromResource("candleLightExample.json")
  val jsonText = s.getLines().mkString
  s.close()
  val node = mapper.readTree(jsonText)
  println(s"node:$node")
}
