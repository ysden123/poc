/*
 * Copyright (c) 2023. StulSoft
 */

package com.stulsoft.poc.json.json4s

import com.typesafe.scalalogging.LazyLogging
import org.json4s.*
import org.json4s.jackson.Serialization.read
import org.json4s.jackson.Serialization.write

case class Object4SerializerEx1(name: String)

case object MySerializerE1 extends CustomSerializer[Object4SerializerEx1](_ => ( {
  case jObject: JObject =>
    val theName = (jObject \ "name").toOption
    val name = theName match
      case Some(JString(aName)) => aName
      case None => throw new MappingException("""Can't extract Object4SerializerEx1: "name" is missing """)
    Object4SerializerEx1(name)
  case _ => throw new MappingException("Can't extract Object4SerializerEx1")
}, {
  case object4SerializerEx1: Object4SerializerEx1 =>
    val jObject: JObject = JObject("name" -> JString(object4SerializerEx1.name))
    jObject
}
))

object CustomSerializerEx1 extends LazyLogging:
  given formats: Formats = DefaultFormats + MySerializerE1

  def test1(): Unit =
    logger.info("==>test1")
    val json =
      """
        |{
          "name": "name 1"
        |}
        |""".stripMargin

    val object4SerializerEx1 = read[Object4SerializerEx1](json)
    println(object4SerializerEx1)

    val json2 = write[Object4SerializerEx1](object4SerializerEx1)
    println(json2)

  def main(args: Array[String]): Unit =
    logger.info("==>main")
    test1()