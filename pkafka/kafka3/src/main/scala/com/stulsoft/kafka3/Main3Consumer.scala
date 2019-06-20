/*
 * Copyright (c) 2017. Yuriy Stul
 */

package com.stulsoft.kafka3

import com.typesafe.scalalogging.LazyLogging

import scala.concurrent.Await
import scala.concurrent.duration.Duration

/**
  * @author Yuriy Stul
  */
object Main3Consumer extends App with LazyLogging {
  val topic = "main3TestTopic"
  logger.info("==>Main3Consumer")

  //  val consumer = new Consumer("test", AutoCommit.DisabledAutoCommit, AutoOffsetRest.Earliest, topic)
  val consumer = new Consumer("test", AutoCommit.DisabledAutoCommit, AutoOffsetRest.Latest, Commit.NoCommit, topic)
  val startedConsumer = consumer.start()

  //  Thread.sleep(5000)
  println("Enter line to exit...")
  Console.in.readLine()
  logger.info("Stopping consumer...")
  consumer.stop()

  Await.ready(startedConsumer, Duration.Inf)

  logger.info("<==Main3Consumer")
}
