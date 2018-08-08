/*
 * Copyright (c) 2017. Yuriy Stul
 */

package com.stulsoft.pquartz.examples

import com.typesafe.scalalogging.LazyLogging
import org.quartz.JobBuilder._
import org.quartz.SimpleScheduleBuilder._
import org.quartz.TriggerBuilder._
import org.quartz._
import org.quartz.impl.StdSchedulerFactory

class MyJob1 extends Job with LazyLogging {
  override def execute(context: JobExecutionContext): Unit = logger.info("Hello World!  MyJob1 is executing.")
}

/**
  * Simple schedule
  *
  * @author Yuriy Stul
  */
object Example1 extends App with LazyLogging {

  // Grab the Scheduler instance from the Factory

  val scheduler = StdSchedulerFactory.getDefaultScheduler

  // define the job and tie it to our MyJob class
  val job = newJob(classOf[MyJob1]).withIdentity("job1", "group1").build

  // Trigger the job to run now, and then repeat every 40 seconds
  val trigger = newTrigger()
    .withIdentity("trigger1", "group1")
    .startNow()
    .withSchedule(simpleSchedule()
      .withIntervalInSeconds(40) // Every 40 seconds from now
      .repeatForever())
    .build()

  // Tell quartz to schedule the job using our trigger
  scheduler.scheduleJob(job, trigger)

  // Start it off
  scheduler.start()
}
