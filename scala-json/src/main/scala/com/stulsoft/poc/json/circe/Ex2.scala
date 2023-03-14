/*
 * Copyright (c) 2023. StulSoft
 */

package com.stulsoft.poc.json.circe

import com.typesafe.scalalogging.LazyLogging
import io.circe.*
import io.circe.generic.auto.*
import io.circe.parser.*
import io.circe.syntax.*


case class Bar2(xs: List[String])

case class Qux2(i: Int, d: Option[Double])

case class Combine2(bar: Bar2, qux: Qux2)

object Ex2 extends LazyLogging:
  private def test1(): Unit =
    logger.info("==>test1")
    val qux = Qux2(13, Some(14.0))

    val json = qux.asJson.noSpaces
    println(json)

    val decodedQux = decode[Qux2](json)
    println(decodedQux)

  private def test2(): Unit =
    logger.info("==>test2")
    val bar = Bar2(List("s1", "s2", "s 3"))

    val json = bar.asJson.noSpaces
    println(json)

    val decodedBar = decode[Bar2](json)
    println(decodedBar)

  private def test3(): Unit =
    logger.info("==>test3")
    val qux = Qux2(13, Some(14.0))
    val bar = Bar2(List("s1", "s2", "s 3"))
    val combine = Combine2(bar, qux)
    val json = combine.asJson.noSpaces
    println(json)

    val decodedCombine = decode[Combine2](json)
    println(decodedCombine)

  private def test4(): Unit =
    logger.info("==>test4")
    var qux = decode[Qux2]("{}")
    println(qux) // Left(DecodingFailure at .i: Missing required field)

    qux = decode[Qux2]("""{"i":123}""")
    println(qux)

    qux = decode[Qux2]("""{"d":3.0, "i":123}""")
    println(qux)

  def main(args: Array[String]): Unit =
    logger.info("==>main")
    test1()
    test2()
    test3()
    test4()
