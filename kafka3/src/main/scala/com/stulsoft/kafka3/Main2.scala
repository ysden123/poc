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
object Main2 extends App with LazyLogging {
  val topic = "main1TestTopic"
  logger.info("==>Main2")

  val consumer = new Consumer2(topic)
  val startedConsumer = consumer.start()

  Thread.sleep(1500)
  logger.info("Stopping consumer...")
  consumer.stop()

  Await.ready(startedConsumer, Duration.Inf)


  logger.info("<==Main2")
}
