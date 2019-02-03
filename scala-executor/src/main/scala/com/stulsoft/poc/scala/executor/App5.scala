/*
 * Copyright (c) 2019. Yuriy Stul
 */

package com.stulsoft.poc.scala.executor

import com.typesafe.scalalogging.LazyLogging
import org.quartz.impl.StdSchedulerFactory
import org.quartz.JobBuilder._
import org.quartz.TriggerBuilder._
import org.quartz.SimpleScheduleBuilder._


/** Runs GoodJob, BadJob, and BadJobWithTryCatch as Quartz jobs.
  *
  * Uses Quartz.
  *
  * @author Yuriy Stul
  */
object App5 extends App with LazyLogging {
  def start(): Unit = {
    logger.info("==>start")
    val scheduler = StdSchedulerFactory.getDefaultScheduler

    scheduler.start()

    val goodJob = newJob(classOf[QuartzGoodJob])
      .withIdentity("goodJob", "test")
      .build()
    val goodJobTrigger = newTrigger()
      .withIdentity("goodJobTrigger", "test")
      .startNow()
      .withSchedule(simpleSchedule()
        .withIntervalInSeconds(1)
        .repeatForever())
      .build()

    val badJob = newJob(classOf[QuartzBadJob])
      .withIdentity("badJob", "test")
      .build()
    val badJobTrigger = newTrigger()
      .withIdentity("badJobTrigger", "test")
      .startNow()
      .withSchedule(simpleSchedule()
        .withIntervalInSeconds(1)
        .repeatForever())
      .build()

    val badJobWithTryCatch = newJob(classOf[QuartzBadJobWithTryCatch])
      .withIdentity("badJobWithTryCatch", "test")
      .build()
    val badJobTriggerWithTryCatch = newTrigger()
      .withIdentity("badJobWithTryCatchTrigger", "test")
      .startNow()
      .withSchedule(simpleSchedule()
        .withIntervalInSeconds(1)
        .repeatForever())
      .build()

    scheduler.scheduleJob(goodJob, goodJobTrigger)
    scheduler.scheduleJob(badJob, badJobTrigger)
    scheduler.scheduleJob(badJobWithTryCatch, badJobTriggerWithTryCatch)

    Thread.sleep(10000)
    logger.info("<==start")
    scheduler.shutdown()
  }

  start()
}
