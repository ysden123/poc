/*
 * Copyright (c) 2017. Yuriy Stul
 */

package com.stulsoft.pquartz.examples

import com.typesafe.scalalogging.LazyLogging
import org.quartz.JobBuilder._
import org.quartz.TriggerBuilder._
import org.quartz._
import org.quartz.impl.StdSchedulerFactory

class MyJob5 extends Job {
  override def execute(context: JobExecutionContext): Unit = {
    val someConf = context.getScheduler.getContext.get("someConf").asInstanceOf[SomeConf]
    println(s"Hello World!  MyJob5 is executing. ${someConf.getParameter}")
    throw new JobExecutionException("test exception")
  }
}

class MyJobListener5 extends JobListener{
  override def getName: String = "MyJobListener5"

  override def jobToBeExecuted(context: JobExecutionContext): Unit = {}

  override def jobWasExecuted(context: JobExecutionContext, jobException: JobExecutionException): Unit = {
    if (jobException != null){
      println(s"Failed job. Message: ${jobException.getMessage}")
    }
  }

  override def jobExecutionVetoed(context: JobExecutionContext): Unit = {}
}

/**
  * Error handling
  *
  * @author Yuriy Stul
  */
object Example5 extends App with LazyLogging {

  // Grab the Scheduler instance from the Factory

  val scheduler = StdSchedulerFactory.getDefaultScheduler

  scheduler.getContext.put("someConf", new SomeConf)

  // define the job and tie it to our MyJob class
  val job = newJob(classOf[MyJob5]).withIdentity("job1", "group1").build

  val groupName="group1"
  val myJobListener = new MyJobListener5

  // Trigger the job to run now, and then repeat every 40 seconds
  val trigger = newTrigger()
    .withIdentity("trigger1", groupName)
    .startNow()
    .withSchedule(CronScheduleBuilder.cronSchedule("0/10 * * * * ?")) // Every 10 seconds (0, 10, ...)
    .build()

  // Tell quartz to schedule the job using our trigger
  scheduler.scheduleJob(job, trigger)

  // Registering A JobListener With The Scheduler To Listen To All Jobs
  scheduler.getListenerManager.addJobListener(myJobListener)

  // Start it off
  scheduler.start()
}
