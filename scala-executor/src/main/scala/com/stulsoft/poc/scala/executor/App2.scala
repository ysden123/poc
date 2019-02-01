/*
 * Copyright (c) 2019. Yuriy Stul
 */

package com.stulsoft.poc.scala.executor

import java.util.concurrent.{Executors, TimeUnit}

import com.typesafe.scalalogging.LazyLogging

/** Runs GoodJob, BadJob, and BadJobWithTryCatch as scheduled jobs.
  *
  * Uses standard Java concurrent Scheduling.
  *
  * @author Yuriy Stul
  */
object App2 extends App with LazyLogging {
  def start(): Unit = {
    logger.info("==>start")

    val executor = Executors.newScheduledThreadPool(3)
    executor.scheduleWithFixedDelay(new GoodJob, 0, 1, TimeUnit.SECONDS)
    executor.scheduleWithFixedDelay(new BadJob, 0, 1, TimeUnit.SECONDS)
    executor.scheduleWithFixedDelay(new BadJobWithTryCatch, 0, 1, TimeUnit.SECONDS)

    Thread.sleep(10000)
    logger.info("<==start")
    System.exit(0)
  }

  start()
}
