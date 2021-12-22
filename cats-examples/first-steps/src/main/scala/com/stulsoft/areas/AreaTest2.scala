/*
 * Copyright (c) 2021. StulSoft
 */

package com.stulsoft.areas

import AreaInstances._
import ShapeAreaSyntax._

/** Interface Syntax
 *
 * @author Yuriy Stul
 */
object AreaTest2 extends App {
  val areaOfRectangle = Rectangle(2.0, 3.0).areaOf
  println(s"areaOfRectangle = $areaOfRectangle")

  val areaOfCircle = Circle( 3.0).areaOf
  println(s"areaOfCircle = $areaOfCircle")
}
