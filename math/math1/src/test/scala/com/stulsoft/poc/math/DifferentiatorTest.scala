/*
 * Copyright (c) 2021. StulSoft
 */

package com.stulsoft.poc.math

import org.scalatest.funsuite.AnyFunSuite

import scala.util.{Failure, Success}

/**
 * @author Yuriy Stul
 */
class DifferentiatorTest extends AnyFunSuite {
  test("differentiate arg = 2") {
    val dif = Differentiator.differentiate(x => x * 3, 1.0, 2.0)
    assert(dif.isSuccess)
    assert(dif.get == 3.0)
  }

  test("differentiate arg = 3") {
    val dif = Differentiator.differentiate(x => x * 3, 1.0, 3.0)
    assert(dif.isSuccess)
    assert(dif.get == 3.0)
  }

  test("differentiate arg = 0") {
    val dif = Differentiator.differentiate(x => x * 3, 1.0, 0.0)
    assert(dif.isSuccess)
    assert(dif.get == 3.0)
  }

  test("differentiate delta = 0") {
    val dif = Differentiator.differentiate(x => x * 3, 0.0, 0.0)
    dif match {
      case Success(_)=>
        fail("Uncaught exception")
      case Failure(exception) =>
        exception.printStackTrace()
        succeed
    }
  }
}
