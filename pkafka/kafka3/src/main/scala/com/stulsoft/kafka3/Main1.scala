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
object Main1 extends App with LazyLogging {
  val topic = "main1TestTopic"
  logger.info("==>Main1")
  val producer = new Producer1(topic, 100)

  val startedProducer = producer.start()

  val consumer = new Consumer1(topic)
  val startedConsumer = consumer.start()

  Thread.sleep(1500)
  logger.info("Stopping producer...")
  producer.stop()
  logger.info("Stopping consumer...")
  consumer.stop()

  Await.ready(startedProducer, Duration.Inf)


  logger.info("<==Main1")
}
