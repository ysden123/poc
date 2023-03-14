/*
 * Copyright (c) 2023. StulSoft
 */

package com.stulsoft.poc.json.json4s

import com.typesafe.scalalogging.LazyLogging
import org.json4s.*
import org.json4s.jackson.Serialization.write

import scala.collection.mutable.ListBuffer

object BuilderEx1 extends LazyLogging:
  private def test1(): Unit =
    logger.info("==>test1")

    given formats: Formats = DefaultFormats

    val f1 = JField("name1", JString("string value"))
    val f2 = JField("name2", JInt(123))
    val f3 = JField("name2", JBool(true))
    val fields = List(f1, f2, f3)
    val jObject = JObject(fields)
    val json = write(jObject)
    println(s"json: $json")

  private def test2(): Unit =
    logger.info("==>test2")

    given formats: Formats = DefaultFormats

    val f1 = JField("name1", JString("string value"))
    val f2 = JField("name2", JInt(123))
    val f3 = JField("name2", JBool(true))
    val fields = f1 :: f2 :: f3 :: Nil
    val jObject = JObject(fields)
    val json = write(jObject)
    println(s"json: $json")

  private def test3(): Unit =
    logger.info("==>test3")

    given formats: Formats = DefaultFormats

    val f1 = JField("name1", JString("string value"))
    val f2 = JField("name2", JInt(123))
    val f3 = JField("name2", JBool(true))
    val fields = new ListBuffer[JField]()
    fields += f1
    fields += f2
    fields += f3
    val jObject = JObject(fields.toList)
    val json = write(jObject)
    println(s"json: $json")

  private def buildField[T](name: String, value: T): JField =
    val jValue: JValue = value match
      case v: String => JString(v)
      case v: Int => JInt(v)
      case v: Long => JLong(v)
      case v: Double => JDouble(v)
      case v: Boolean => JBool(v)
      case x => throw new MappingException(s"Unsupported type $x")

    JField(name, jValue)

  private def test4(): Unit =
    logger.info("==>test4")
    val jf1=buildField("name1", 123)
    println(s"jf1: $jf1")
    val jf2=buildField("name2", 4.0)
    println(s"jf2: $jf2")
    val jf3=buildField("name3", "text")
    println(s"jf3: $jf3")

  def main(args: Array[String]): Unit =
    logger.info("==>main")
    test1()
    test2()
    test3()
    test4()
