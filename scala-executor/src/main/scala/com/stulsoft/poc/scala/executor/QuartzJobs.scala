/*
 * Copyright (c) 2019. Yuriy Stul
 */

package com.stulsoft.poc.scala.executor

import com.typesafe.scalalogging.LazyLogging
import org.quartz.{Job, JobExecutionContext}

/**
  * @author Yuriy Stul
  */
class QuartzGoodJob extends Job with LazyLogging {
  override def execute(context: JobExecutionContext): Unit = {
    logger.info("==>job")
    Thread.sleep(1000)
    logger.info("Done")
    logger.info("<==job")
  }
}

/**
  * @author Yuriy Stul
  */
class QuartzBadJob extends Job with LazyLogging {
  override def execute(context: JobExecutionContext): Unit = {
    logger.info("==>job")
    Thread.sleep(1000)
    throw new RuntimeException("Test exception in BadJob")
  }
}

/**
  * @author Yuriy Stul
  */
class QuartzBadJobWithTryCatch extends Job with LazyLogging {
  override def execute(context: JobExecutionContext): Unit = {
    logger.info("==>job")
    try {
      Thread.sleep(1000)
      throw new RuntimeException("Test exception in BadJobWithTryCatch")
    }catch{
      case e:Exception =>
        logger.error(s"Failed execution of QuartzBadJobWithTryCatch. Error: ${e.getMessage}")
    }
  }
}
