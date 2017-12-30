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
object Main3Producer extends App with LazyLogging {
  val topic = "main3TestTopic"
  logger.info("==>Main3Producer")

  val producer = new Producer(topic, 153)
  val startedProducer = producer.start()


  Thread.sleep(5000)
  logger.info("Stopping producer...")
  producer.stop()

  Await.ready(startedProducer, Duration.Inf)

  logger.info("<==Main3Producer")
}
