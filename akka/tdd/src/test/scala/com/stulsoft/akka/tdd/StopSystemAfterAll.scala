/*
 * Copyright (c) 2017. Yuriy Stul
 */

package com.stulsoft.akka.tdd

import akka.testkit.TestKit
import org.scalatest.{BeforeAndAfterAll, Suite}

/**
  * @see Akka in Action by Raymond Roestenburg, Rob Bakker and Rob Williams
  * @author Yuriy Stul
  */
trait StopSystemAfterAll extends BeforeAndAfterAll {
  this: TestKit with Suite =>
  override protected def afterAll() {
    super.afterAll()
    system.terminate()
  }
}
