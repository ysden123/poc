/*
 * Copyright (c) 2021. Webpals
 */

/**
 * @author Yuriy Stul
 */
package com.stulsoft.poc.math

import org.scalatest.funsuite.AnyFunSuite

class EquationSolverTest extends AnyFunSuite {
  test("has solution") {
    val x = EquationSolver.newtonRaphson(f1, 5.0, 0.5, 0.1)
    assert(x == 0.0)
  }

  test("has solution, start minus") {
    val x = EquationSolver.newtonRaphson(f1, -5.0, 0.5, 0.1)
    assert(x == 0.0)
  }

  test("has solution with const") {
    val precision = 0.1
    val x = EquationSolver.newtonRaphson(f2, 5.0, 0.5, precision)
    assert(f2(x).abs <= precision)
  }

  test("has solution with power") {
    val precision = 1E-10
    val x = EquationSolver.newtonRaphson(f3, 5.0, 0.5, precision)
    assert(f3(x).abs <= precision)
  }

  def f1(x: Double): Double = {
    x * 3.0
  }

  def f2(x: Double): Double = {
    7.0 + x * 3.0
  }

  def f3(x: Double): Double = {
    Math.pow(x, 2)
  }
}
