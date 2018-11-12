/*
 * Copyright (c) 2018. Yuriy Stul
 */

package com.stulsoft.scheduler1.hourly

import java.util.concurrent.{Executors, TimeUnit}

import com.typesafe.scalalogging.LazyLogging

/**
  * @author Yuriy Stul
  */
object HourlyRunner extends App with LazyLogging {
  def run(): Unit = {
    logger.info("==>run")
    val scheduledExecutorService = Executors.newScheduledThreadPool(1)

    scheduledExecutorService.scheduleWithFixedDelay(() => {
      logger.info("==>job")
      Thread.sleep(1000)
      logger.info("<==job")
    }, 0, 1, TimeUnit.HOURS)
  }

  run()

  while (true) {
    Thread.sleep(10000)
  }
}
