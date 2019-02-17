/*
 * Copyright (c) 2019. Yuriy Stul
 */

package com.stulsoft.scala.test.flat.spec

import com.typesafe.scalalogging.LazyLogging

/**
  * @author Yuriy Stul
  */
class SomeClass extends LazyLogging {
  def foo(): String = {
    logger.info("==>foo")
    logger.info("<==foo")
    "foo"
  }

  def parameter():String={
    logger.info("==>parameter")
    logger.info("<==parameter")
    Config.parameter
  }
}
