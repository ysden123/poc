/*
 * Copyright (c) 2018. Yuriy Stul
 */

package com.stulsoft.poc.pomtest4.scala

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.{FunSuite, Matchers}

/**
  * @author Yuriy Stul
  */
@RunWith(classOf[JUnitRunner])
class SomeScalaClassTest extends FunSuite with Matchers {

  test("testFoo") {
    new SomeScalaClass foo() should not be null
  }

}
