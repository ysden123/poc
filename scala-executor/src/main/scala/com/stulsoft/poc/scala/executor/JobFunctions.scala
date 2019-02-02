/*
 * Copyright (c) 2019. Yuriy Stul
 */

package com.stulsoft.poc.scala.executor

import com.typesafe.scalalogging.LazyLogging

/**
  * Good job function - no exception
  */
object GoodJobFunction extends LazyLogging {
  def job(): Unit = {
    logger.info("==>job")
    Thread.sleep(1000)
    logger.info("Done")
    logger.info("<==job")
  }
}

/**
  * Bad job function - exception and no exception handler
  */
object BadJobFunction extends LazyLogging {
  def job(): Unit = {
    logger.info("==>job")
    Thread.sleep(1000)
    throw new RuntimeException("Test exception in BadJob")
  }
}

/**
  * Bad job function - exception and exception handler
  */
object BadJobFunctionWithTryCatch extends LazyLogging {
  def job(): Unit = {
    try {
      logger.info("==>job")
      Thread.sleep(1000)
      throw new RuntimeException("Test exception in BadJobWithTryCatch")
    } catch {
      case e: Exception => logger.error(e.getMessage)
    }
  }
}
