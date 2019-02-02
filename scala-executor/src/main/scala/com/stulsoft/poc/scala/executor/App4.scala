/*
 * Copyright (c) 2019. Yuriy Stul
 */

package com.stulsoft.poc.scala.executor

import akka.actor.ActorSystem
import com.typesafe.scalalogging.LazyLogging

import scala.concurrent.duration._

/** Runs GoodJob, BadJob, and BadJobWithTryCatch as scheduled runnable jobs.
  *
  * Uses Akka Actor Scheduling. Calls jobs as functions.
  *
  * @author Yuriy Stul
  */
object App4 extends App with LazyLogging {

  def start(): Unit = {
    logger.info("==>start")

    val system = ActorSystem("App4")

    import scala.concurrent.ExecutionContext.Implicits.global

    system.scheduler.schedule(0.seconds, 1.seconds)(GoodJobFunction.job())
    system.scheduler.schedule(0.seconds, 1.seconds)(BadJobFunction.job())
    system.scheduler.schedule(0.seconds, 1.seconds)(BadJobFunctionWithTryCatch.job())

    Thread.sleep(10000)
    logger.info("<==start")
    system.terminate()
  }

  start()

}
