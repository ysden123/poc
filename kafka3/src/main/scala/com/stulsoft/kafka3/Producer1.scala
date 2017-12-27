/*
 * Copyright (c) 2017. Yuriy Stul
 */

package com.stulsoft.kafka3

import java.util.Properties

import com.typesafe.scalalogging.LazyLogging
import org.apache.kafka.clients.producer.KafkaProducer

import scala.concurrent.Future

/**
  * @author Yuriy Stul
  */
final class Producer1(val interval: Int) extends LazyLogging {
  private var continueExecuting = false

  import scala.concurrent.ExecutionContext.Implicits.global

  def start(): Future[Unit] = Future {
    logger.info("Started Producer1")
    continueExecuting = true

    val props: Properties = new Properties()
    props.put("bootstrap.servers", "localhost:9092,localhost:9093")
    props.put("acks", "all")
    props.put("retries", Int.box(0))
    props.put("batch.size", Int.box(16384))
    props.put("linger.ms", Int.box(1))
    props.put("buffer.memory", Int.box(33554432))
    props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer")
    props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer")
    val producer = new KafkaProducer[String, String](props)

    while (continueExecuting) {
      // send here
      Thread.sleep(interval)
    }

    producer.close()
    logger.info("Finished Producer1")
  }

  def stop(): Unit = continueExecuting = false
}
