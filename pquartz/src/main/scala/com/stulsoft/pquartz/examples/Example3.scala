/*
 * Copyright (c) 2017. Yuriy Stul
 */

package com.stulsoft.pquartz.examples

import com.typesafe.scalalogging.LazyLogging
import org.quartz.JobBuilder._
import org.quartz.TriggerBuilder._
import org.quartz._
import org.quartz.impl.StdSchedulerFactory

class MyJob3 extends Job {
  override def execute(context: JobExecutionContext): Unit = println("Hello World!  MyJob3 is executing.")
}

/**
  * Cron expression
  *
  * @author Yuriy Stul
  */
object Example3 extends App with LazyLogging {

  // Grab the Scheduler instance from the Factory

  val scheduler = StdSchedulerFactory.getDefaultScheduler

  // define the job and tie it to our MyJob class
  val job = newJob(classOf[MyJob3]).withIdentity("job1", "group1").build

  // Trigger the job to run now, and then repeat every 10 seconds
  val trigger = newTrigger()
    .withIdentity("trigger1", "group1")
    .startNow()
    .withSchedule(CronScheduleBuilder.cronSchedule("0/10 * * * * ?")) // Every 10 seconds (0, 10, ...)
    .build()

  // Tell quartz to schedule the job using our trigger
  scheduler.scheduleJob(job, trigger)

  // Start it off
  scheduler.start()
}
