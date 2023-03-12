/*
 * Copyright (c) 2023. StulSoft
 */

package com.stulsoft.poc.json.json4s

import com.typesafe.scalalogging.LazyLogging
import org.json4s.*
import org.json4s.jackson.Serialization.write

import scala.collection.mutable.ListBuffer

object BuilderEx1 extends LazyLogging:
  private def test1():Unit=
    logger.info("==>test1")
    given formats:Formats = DefaultFormats
    val f1 = JField("name1", JString("string value"))
    val f2 = JField("name2", JInt(123))
    val f3 = JField("name2", JBool(true))
    val fields = List(f1,f2,f3)
    val jObject=JObject(fields)
    val json = write(jObject)
    println(s"json: $json")

  private def test2():Unit=
    logger.info("==>test2")
    given formats:Formats = DefaultFormats
    val f1 = JField("name1", JString("string value"))
    val f2 = JField("name2", JInt(123))
    val f3 = JField("name2", JBool(true))
    val fields = f1 :: f2 :: f3 :: Nil
    val jObject=JObject(fields)
    val json = write(jObject)
    println(s"json: $json")

  private def test3():Unit=
    logger.info("==>test3")
    given formats:Formats = DefaultFormats
    val f1 = JField("name1", JString("string value"))
    val f2 = JField("name2", JInt(123))
    val f3 = JField("name2", JBool(true))
    val fields = new ListBuffer[JField]()
    fields += f1
    fields += f2
    fields += f3
    val jObject=JObject(fields.toList)
    val json = write(jObject)
    println(s"json: $json")

  def main(args: Array[String]): Unit =
    logger.info("==>main")
    test1()
    test2()
    test3()
