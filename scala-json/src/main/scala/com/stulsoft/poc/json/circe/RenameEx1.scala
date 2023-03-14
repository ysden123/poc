/*
 * Copyright (c) 2023. StulSoft
 */

package com.stulsoft.poc.json.circe

import com.typesafe.scalalogging.LazyLogging
import io.circe.*
import io.circe.generic.auto.*
import io.circe.parser.*
import io.circe.syntax.*

case class TheObject4Rename(theObject: String)

object RenameEx1 extends LazyLogging:
  given encoder: Encoder[TheObject4Rename] =
    (theObject4Rename: TheObject4Rename) => Json.obj("object" -> theObject4Rename.theObject.asJson)

  given decoder: Decoder[TheObject4Rename] =
    (c: HCursor) =>
      for {
        theObject <- c.downField("object").as[String]
      } yield TheObject4Rename(theObject)

  private def test1(): Unit =
    logger.info("==>test1")


    val theObject4Rename = TheObject4Rename("Initial code value")
    val json = theObject4Rename.asJson.noSpaces
    println(json)

    val decodedTheObject4Rename = decode[TheObject4Rename](json)
    println(decodedTheObject4Rename)

  private def test2(): Unit =
    logger.info("==>test2")
    val json =
      """
        {
        "object":"some value of an object"
        }
        """
    val decodedTheObject4Rename = decode[TheObject4Rename](json)
    println(decodedTheObject4Rename)

    decodedTheObject4Rename match
      case Right(decodedTheObject4RenameInstance) =>
        println(decodedTheObject4RenameInstance)
        val json2 = decodedTheObject4RenameInstance.asJson.noSpaces
        println(json2)
      case Left(error:Error) =>
        println(error.getMessage)

  def main(args: Array[String]): Unit =
    logger.info("==>main")
    test1()
    test2()
