package com.stulsoft.producer1

import java.util.Properties
import java.util.concurrent.TimeUnit

import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}
import org.slf4j.{Logger, LoggerFactory}

/**
  * @author Yuriy Stul.
  */
object Producer extends App {
  val logger: Logger = LoggerFactory.getLogger(Producer.getClass)
  logger.info("Started producer")
  senMessages()

  def senMessages(): Unit = {
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
    logger.info("Send messages")
    while (true) {
      try {
        val future = producer.send(new ProducerRecord[String, String]("myClusterTopic", "theKey", "the value"))
        logger.info("Sent message")
        try {
          val result = future.get(10, TimeUnit.SECONDS)
          logger.info("Succeeded send message {}", result)
        }
        catch {
          case e: Exception => logger.error("Failed send message. Error: {}", e.getMessage)
        }
      }
      catch {
        case e: Throwable => logger.error("Failed sending message with error {}", e.getMessage)
      }
      logger.info("Wait...")
      Thread.sleep(10000)
    }
  }
}
