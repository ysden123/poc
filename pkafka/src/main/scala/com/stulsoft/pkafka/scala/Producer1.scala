package com.stulsoft.pkafka.scala

import java.util.Properties
import java.util.concurrent.{Future, TimeUnit}

import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord, RecordMetadata}
import org.slf4j.{Logger, LoggerFactory}

import scala.collection.mutable.ListBuffer

/**
  * Producer
  *
  * @author Yuriy Stul
  */
object Producer1 {
  val logger: Logger = LoggerFactory.getLogger(Producer1.getClass)

  /**
    * Sends tests messages
    */
  def sendMessages(): Unit = {
    if (!CheckConnection.checkConnection) {
      logger.error("Kafka server is unavailable.")
      System.exit(1)
    }
    val props: Properties = new Properties()
    props.setProperty("bootstrap.servers", "localhost:9092")
    props.put("acks", "all")
    props.put("retries", Int.box(0))
    props.put("batch.size", Int.box(16384))
    props.put("linger.ms", Int.box(1))
    props.put("buffer.memory", Int.box(33554432))
    props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer")
    props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer")

    val producer = new KafkaProducer[String, String](props)
    val futures = ListBuffer[Future[RecordMetadata]]()

    for (i <- 0 to 1) {
      try {
        logger.info("Send message No.{}", i)
        val future = producer.send(new ProducerRecord[String, String]("my-topic", Integer.toString(i), Integer.toString(i)))
        futures += future
      }
      catch {
        case e: Throwable => logger.error("Error {}", e.getMessage)
      }
    }
    logger.info("Sent {} messages", futures.size)

    futures.foreach(f => {
      try {
        val result = f.get(1, TimeUnit.SECONDS)
        logger.info("Succeeded send message {}", result)
      }
      catch {
        case e: Exception => logger.error(s"Failed send $f. Error: ${e.getMessage}")
      }
    })

    producer.close()
  }

  def main(args: Array[String]): Unit = {
    sendMessages()
  }
}
