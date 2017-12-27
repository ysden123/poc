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
  logger.info("==>Main1")
  val producer = new Producer1(500)

  var startedProducer = producer.start()

  Thread.sleep(1500)
  logger.info("Stopping producer...")
  producer.stop()

  Await.ready(startedProducer, Duration.Inf)


  logger.info("<==Main1")
}
