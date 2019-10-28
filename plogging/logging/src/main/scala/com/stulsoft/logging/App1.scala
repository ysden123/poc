/*
 * Copyright (c) 2018. Yuriy Stul
 */

package com.stulsoft.logging

import com.typesafe.scalalogging.LazyLogging


/**
  * @author Yuriy Stul
  */
object App1 extends App with LazyLogging {
  logger.info("Test")
}
