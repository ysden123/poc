/*
 * Copyright (c) 2019. Yuriy Stul 
 */

package com.stulsoft.first.steps

import cats.Show
import com.typesafe.scalalogging.LazyLogging
import cats.instances.int._
import cats.instances.string._

/** Importing Default Instances
 *
 * @author Yuriy Stul
 */
object ShowEx1 extends App with LazyLogging {
  val showInt: Show[Int] = Show.apply[Int]
  val showString: Show[String] = Show.apply[String]

  println(s"showInt.show(123) = ${showInt.show(123)}")
  println(s"""showString.show("text") = ${showString.show("text")}""")
}
