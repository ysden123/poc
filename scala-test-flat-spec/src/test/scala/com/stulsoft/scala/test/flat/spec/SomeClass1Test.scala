/*
 * Copyright (c) 2019. Yuriy Stul
 */

/**
  * @author Yuriy Stul
  */
package com.stulsoft.scala.test.flat.spec

import com.typesafe.scalalogging.LazyLogging
import org.scalatest.{BeforeAndAfterAll, FlatSpec, Matchers}

class SomeClass1Test
  extends FlatSpec
    with BeforeAndAfterAll
    with Matchers
    with LazyLogging {

  behavior of "SomeClass"

  "foo" should "return foo" in {
    val sc = new SomeClass
    sc.foo() shouldBe "foo"
  }

  "parameter" should "return 'test1 value'" in {
    val sc = new SomeClass
    sc.parameter() shouldBe "test1 value"
  }

  override protected def beforeAll(): Unit = {
    logger.info("beforeAll")
    Config.reinitialize("test1.application.conf")
    super.beforeAll()
  }

  override protected def afterAll(): Unit = {
    logger.info("afterAll")
    super.afterAll()
  }
}
