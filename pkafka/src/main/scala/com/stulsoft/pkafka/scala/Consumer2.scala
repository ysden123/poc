package com.stulsoft.pkafka.scala

import java.util.{Collections, Properties}

import com.typesafe.scalalogging.LazyLogging
import org.apache.kafka.clients.consumer.KafkaConsumer

/**
  * Consumer for work with NodeJS project.
  *
  * See pkafka-node project, code in producer1.js.
  *
  * Test flow:
  *    1. Run Consumer2.scala
  *    2. Run producer1.js
  *
  * @author Yuriy Stul
  */
object Consumer2 extends LazyLogging {
  //  val logger: Logger = LoggerFactory.getLogger(Consumer2.getClass)

  def main(args: Array[String]): Unit = {
    readMessages(10)
  }

  /**
    * Reads messages
    *
    * @param numOfMessages specifies number of messages to read
    */
  def readMessages(numOfMessages: Int): Unit = {
    if (!CheckConnection.checkConnection) {
      logger.error("Kafka server is unavailable.")
      System.exit(1)
    }

    val props = new Properties
    props.put("bootstrap.servers", "localhost:9092")
    props.put("group.id", "test")
    props.put("enable.auto.commit", "true")
    props.put("auto.commit.interval.ms", "1000")
    props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")
    props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")

    val consumer = new KafkaConsumer[String, String](props)
    consumer.subscribe(Collections.singletonList("topic1"))
    var msgCount = 0
    while (msgCount < numOfMessages) {
      val records = consumer.poll(100)
      records.forEach(record => {
        logger.info(s"offset = ${record.offset}, key = ${record.key}, value = ${record.value}")
        msgCount += 1
      })
      consumer.commitSync()
    }

    logger.info(s"Received $msgCount messages")

    consumer.unsubscribe()
    consumer.close()
  }
}
