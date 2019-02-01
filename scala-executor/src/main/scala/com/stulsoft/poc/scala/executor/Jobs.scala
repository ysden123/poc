/*
 * Copyright (c) 2019. Yuriy Stul
 */

package com.stulsoft.poc.scala.executor

import com.typesafe.scalalogging.LazyLogging


/**
  * Good job - no exception
  */
class GoodJob extends Runnable with LazyLogging {
  override def run(): Unit = {
    logger.info("==>run")
    job()
    logger.info("<==run")
  }

  def job(): Unit = {
    logger.info("==>job")
    Thread.sleep(1000)
    logger.info("Done")
    logger.info("<==job")
  }
}

/**
  * Bad job - exception and no exception handler
  */
class BadJob extends Runnable with LazyLogging {
  override def run(): Unit = {
    logger.info("==>run")
    job()
    logger.info("<==run")
  }

  def job(): Unit = {
    logger.info("==>job")
    Thread.sleep(1000)
    throw new RuntimeException("Test exception in BadJob")
  }
}

/**
  * Bad job - exception and exception handler
  */
class BadJobWithTryCatch extends Runnable with LazyLogging {
  override def run(): Unit = {
    logger.info("==>run")
    try {
      job()
    } catch {
      case e: Exception =>
        logger.error(e.getMessage)
    }
    logger.info("<==run")
  }

  def job(): Unit = {
    logger.info("==>job")
    Thread.sleep(1000)
    throw new RuntimeException("Test exception in BadJobWithTryCatch")
  }
}
