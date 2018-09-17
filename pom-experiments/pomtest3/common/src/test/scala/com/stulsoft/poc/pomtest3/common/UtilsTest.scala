/*
 * Copyright (c) 2018. Yuriy Stul
 */

package com.stulsoft.poc.pomtest3.common

import org.scalatest.{FunSuite, Matchers}

/**
  * @author Yuriy Stul
  */
class UtilsTest extends FunSuite with Matchers {

  test("testFoo") {
    Utils.foo() shouldBe "Some foo text"
  }

}
