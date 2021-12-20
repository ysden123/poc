/*
 * Copyright (c) 2021. Webpals
 */

package com.stulsoft.poc.math

/**
 * @author Yuriy Stul
 */
object EquationSolver {

  def newtonRaphson(func: Double => Double, x1: Double, delta: Double, precision: Double): Double = {
    if (func(x1).abs <= precision)
      x1
    else {
      val f1 = Differentiator.differentiate(func, delta, x1)
      if (f1.isSuccess) {
        val x2 = x1 - func(x1) / f1.get
        if (func(x2).abs <= precision)
          x2
        else
          newtonRaphson(func, x2, delta, precision)
      } else
        throw new RuntimeException("Cannot solve")
    }
  }
}
