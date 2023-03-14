/*
 * Copyright (c) 2023. StulSoft
 */

package com.stulsoft.poc.json.circe

import com.typesafe.scalalogging.LazyLogging
import io.circe.*
import io.circe.generic.auto.*
import io.circe.parser.*
import io.circe.syntax.*

sealed trait Foo

case class Bar(xs: Vector[String]) extends Foo

case class Qux(i: Int, d: Option[Double]) extends Foo

object Ex1 extends LazyLogging:
  private def test1(): Unit =
    logger.info("==>test1")
    val foo: Foo = Qux(13, Some(14.0))

    val json = foo.asJson.noSpaces
    println(json)

    val decodedFoo = decode[Foo](json)
    println(decodedFoo)

  private def test2(): Unit =
    logger.info("==>test2")
    val foo: Foo = Bar(Vector("s1", "s2", "s 3"))

    val json = foo.asJson.noSpaces
    println(json)

    val decodedFoo = decode[Foo](json)
    println(decodedFoo)

  def main(args: Array[String]): Unit =
    logger.info("==>main")
    test1()
    test2()
