/*
 * Copyright (c) 2023. StulSoft
 */

package com.stulsoft.poc.json.circe

import com.typesafe.scalalogging.LazyLogging
import io.circe.*
import io.circe.generic.auto.*
import io.circe.parser.*
import io.circe.syntax.*

case class ObjWithOption(name: Option[String])

object Ex3 extends LazyLogging:

  private def test1(): Unit =
    logger.info("==>test1")
    val json =
      """{
          "name":null
        }""".stripMargin

    decode[ObjWithOption](json) match
      case Right(objWithOption: ObjWithOption) =>
        println(objWithOption)
      case Left(error) =>
        logger.error(error.getMessage, error)

  private def test2(): Unit =
    logger.info("==>test2")
    val json =
      """{
        }""".stripMargin

    decode[ObjWithOption](json) match
      case Right(objWithOption: ObjWithOption) =>
        println(objWithOption)
      case Left(error) =>
        logger.error(error.getMessage, error)

  private def test3(): Unit =
    logger.info("==>test3")
    val json =
      """{{
        }""".stripMargin

    decode[ObjWithOption](json) match
      case Right(objWithOption: ObjWithOption) =>
        println(objWithOption)
      case Left(error) =>
        logger.error(error.getMessage, error)

  private def test4(): Unit =
    logger.info("==>test4")
    val json =
      """{
      "name":"some value",
      "newField":123
        }""".stripMargin

    decode[ObjWithOption](json) match
      case Right(objWithOption: ObjWithOption) =>
        println(objWithOption)
      case Left(error) =>
        logger.error(error.getMessage, error)

  def main(args: Array[String]): Unit =
    logger.info("==>main")
    test1()
    test2()
    test3()
    test4()
