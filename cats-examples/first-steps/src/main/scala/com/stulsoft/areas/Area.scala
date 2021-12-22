/*
 * Copyright (c) 2021. StulSoft
 */

package com.stulsoft.areas

/**
 * @author Yuriy Stul
 */
trait Area[A] {
  def area(a: A): Double
}
