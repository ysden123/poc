/*
 * Copyright (c) 2019. Yuriy Stul
 */

package com.stulsoft.poc.scala.executor

import akka.actor.ActorSystem
import com.typesafe.scalalogging.LazyLogging
import scala.concurrent.duration._

/** Runs GoodJob, BadJob, and BadJobWithTryCatch as scheduled runnable jobs.
  *
  * Uses Akka Actor Scheduling. Calls jobs as Runnable classes.
  *
  * @author Yuriy Stul
  */
object App3 extends App with LazyLogging {

  def start(): Unit = {
    logger.info("==>start")

    val system = ActorSystem("App3")

    import scala.concurrent.ExecutionContext.Implicits.global

    system.scheduler.schedule(0.seconds, 1.seconds, new GoodJob)
    system.scheduler.schedule(0.seconds, 1.seconds, new BadJob)
    system.scheduler.schedule(0.seconds, 1.seconds, new BadJobWithTryCatch)

    Thread.sleep(10000)
    logger.info("<==start")
    system.terminate()
  }

  start()

}
