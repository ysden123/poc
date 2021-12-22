/*
 * Copyright (c) 2021. StulSoft
 */

package com.stulsoft.areas

/**
 * @author Yuriy Stul
 */
object ShapeArea {
  def areaOf[A](a: A)(implicit shape: Area[A]): Double = shape.area(a)
}
