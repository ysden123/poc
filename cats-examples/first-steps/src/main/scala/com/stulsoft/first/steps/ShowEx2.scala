/*
 * Copyright (c) 2019. Yuriy Stul
 */

package com.stulsoft.first.steps

import cats.instances.int._
import cats.instances.string._
import cats.syntax.show._
import com.typesafe.scalalogging.LazyLogging

/** Importing Interface Syntax
 *
 * @author Yuriy Stul
 */
object ShowEx2 extends App with LazyLogging {
  println(s"123.show = ${123.show}")
  println(s""""text".show = ${"text".show}""")
}
