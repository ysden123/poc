/*
 * Copyright (c) 2021. StulSoft
 */

package com.stulsoft.poc.math

import scala.util.Try

/**
 * @author Yuriy Stul
 */
object Differentiator {
  def differentiate(func: Double => Double, delta: Double, arg: Double): Try[Double] = {
    Try {
      (func(arg + delta) - func(arg - delta)) / (delta * 2.0)
    }
  }
}
