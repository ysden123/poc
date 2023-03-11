/*
 * Copyright (c) 2023. StulSoft
 */

package com.stulsoft.poc.json.json4s

import com.typesafe.scalalogging.LazyLogging
import org.json4s.*

case class Object4SerializerE1(name: String)

/*
class MySerializerE1 extends Serializer[Object4SerializerE1] {
  override def deserialize(using format: Formats): PartialFunction[(TypeInfo, JValue), Object4SerializerE1] = {
    case (TypeInfo(Object4SerializerE1, _), json) => json match {
      case JObject(JField("name", JString(name)) :: _) =>
        Object4SerializerE1(name)
      case x =>
        throw new MappingException("Can't convert " + x + " to Object4SerializerE1")
    }
  }

  override def serialize(using format: Formats): PartialFunction[Any, JValue] = {
    case x: Object4SerializerE1 =>
      import JsonDSL.*
      ("name" -> x.name)
  }
}
*/
class MySerializerE1 extends CustomSerializer[Object4SerializerE1] {
/*
  case jsonObject: JObject =>
  val name = (jsonObject \ "name").extract[String]
  Object4SerializerE1(name)
  case object4SerializerE1: Object4SerializerE1 =>
    ("name" -> object4SerializerE1.name)
*/

  override def deserialize(implicit format: Formats): PartialFunction[(TypeInfo, JValue), Object4SerializerE1] = (ti,jv) =>
    val name = (jv \ "name").extract[String]
    Object4SerializerE1(name)

  override def serialize(implicit format: Formats): PartialFunction[Any, JValue] = Object4SerializerE1("test")
}

object CustomSerializerE1 extends LazyLogging:
  def main(args: Array[String]): Unit =
    logger.info("==>main")