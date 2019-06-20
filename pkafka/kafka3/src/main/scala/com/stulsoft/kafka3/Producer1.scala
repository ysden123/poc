/*
 * Copyright (c) 2017. Yuriy Stul
 */

package com.stulsoft.kafka3

import java.util.Properties
import java.util.concurrent.TimeUnit

import com.typesafe.scalalogging.LazyLogging
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}

import scala.concurrent.Future
import scala.util.Random

/**
  * @author Yuriy Stul
  */
final class Producer1(val topic:String, val interval: Int) extends LazyLogging {
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
      sendMessage(producer)
      Thread.sleep(interval)
    }

    producer.close()
    logger.info("Stopped Producer1")
  }

  def sendMessage(producer:KafkaProducer[String,String]): Unit ={
    try {
      val future = producer.send(new ProducerRecord[String, String](topic, "theKey_" + Random.nextInt(), "the value_" + Random.nextInt()))
      logger.info("Sent message")
      try {
        val result = future.get(10, TimeUnit.SECONDS)
        val resultText=s"Succeeded send message. Offset is ${result.offset()}, partition is ${result.partition()}, topic is ${result.topic()}"
        logger.info(resultText)
      }
      catch {
        case e: Exception => logger.error("Failed send message. Error: {}", e.getMessage)
      }
    }
    catch {
      case e: Throwable => logger.error("Failed sending message with error {}", e.getMessage)
    }
  }

  def stop(): Unit = continueExecuting = false
}
