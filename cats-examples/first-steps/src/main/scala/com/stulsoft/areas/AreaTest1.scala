/*
 * Copyright (c) 2021. StulSoft
 */

package com.stulsoft.areas

import AreaInstances._

/** Interface Objects
 *
 * @author Yuriy Stul
 */
object AreaTest1 extends App {
  val rectangle = Rectangle(2.0, 3.0)
  val areaOfRectangle = ShapeArea.areaOf(rectangle)(rectangleArea)
  println(s"areaOfRectangle = $areaOfRectangle")

  val circle = Circle(2.0)
  val areaOfCircle = ShapeArea.areaOf(circle)(circleArea)
  println(s"areaOfCircle = $areaOfCircle")
}
