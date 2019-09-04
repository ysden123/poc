/*
 * Copyright (c) 2019. Yuriy Stul
 */

package com.stulsoft.first.steps

import cats.instances.int._
import cats.instances.string._
import cats.instances.option._
import cats.syntax.eq._
import cats.syntax.option._

/** Equality
 *
 * @author Yuriy Stul
 */
object EqEx1 extends App {
  val c1 = 123 === 321
  val c2 = "ghgjhghj" === "fgfgf"
  // Error: type mismatch  val c3 = 123 === "gjgjgjh"

  val c4 = (Some(1): Option[Int]) === Some(2)
  val c5 = (Some(1):Option[Int]) === None
  val c6 = (Some(1): Option[Int]) === Some(1)

  val c7 = 1.some === none[Int]

  println(s"$c1, $c2, $c4, $c5, $c6, $c7")
}
