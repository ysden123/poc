/*
 * Copyright (c) 2021. StulSoft
 */

package com.stulsoft.areas

/**
 * @author Yuriy Stul
 */
object AreaInstances {
  implicit val rectangleArea: Area[Rectangle] = (a: Rectangle) => a.width * a.length

  implicit val circleArea: Area[Circle] = (a: Circle) => Math.PI * (a.radius * a.radius)
}
