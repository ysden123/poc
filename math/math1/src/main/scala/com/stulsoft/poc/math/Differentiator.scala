/*
 * Copyright (c) 2021. StulSoft
 */

package com.stulsoft.poc.math

import scala.util.Try

/** Differentiate a function
 *
 * @author Yuriy Stul
 */
object Differentiator {
  /**
   * Differentiate a function
   *
   * @param func the function to differentiate
   * @param delta the delta for differentiate
   * @param arg defines the point where to differentiate
   * @return result of differentiate in the specified point
   */
  def differentiate(func: Double => Double, delta: Double, arg: Double): Try[Double] = {
    Try {
      val result = (func(arg + delta) - func(arg - delta)) / (delta * 2.0)
      if (result.isNaN || result.isInfinity)
        throw new IllegalArgumentException("Undefined result, possibly division by zero")
      else
        result
    }
  }
}
