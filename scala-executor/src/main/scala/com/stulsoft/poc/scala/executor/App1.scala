/*
 * Copyright (c) 2019. Yuriy Stul
 */

package com.stulsoft.poc.scala.executor

import java.util.concurrent.{ScheduledExecutorService, TimeUnit}

import com.typesafe.scalalogging.LazyLogging
import org.springframework.scheduling.annotation.SchedulingConfigurer
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler
import org.springframework.scheduling.config.ScheduledTaskRegistrar

/** Runs GoodJob, BadJob, and BadJobWithTryCatch as scheduled jobs.
  *
  * Uses Spring Scheduling.
  *
  * @author Yuriy Stul
  */
object App1 extends App with LazyLogging with SchedulingConfigurer {
  val threadPoolTaskScheduler = new ThreadPoolTaskScheduler()
  var executor: ScheduledExecutorService = _

  /**
    * Configures task (required by Spring)
    *
    * @param taskRegistrar the task registrar
    */
  override def configureTasks(taskRegistrar: ScheduledTaskRegistrar): Unit = {
    taskRegistrar.setScheduler(executor)
  }

  /**
    * Initializes scheduler
    */
  def init(): Unit = {
    logger.info("==>init")
    threadPoolTaskScheduler.setPoolSize(3)
    threadPoolTaskScheduler.initialize()
    logger.info("<==init")
  }

  /**
    * Starts jobs
    */
  def start(): Unit = {
    logger.info("==>start")
    executor = threadPoolTaskScheduler.getScheduledExecutor

    executor.scheduleWithFixedDelay(new GoodJob, 0, 1, TimeUnit.SECONDS)
    executor.scheduleWithFixedDelay(new BadJob, 0, 1, TimeUnit.SECONDS)
    executor.scheduleWithFixedDelay(new BadJobWithTryCatch, 0, 1, TimeUnit.SECONDS)

    Thread.sleep(10000)
    logger.info("<==start")
    System.exit(0)
  }

  init()
  start()

}
