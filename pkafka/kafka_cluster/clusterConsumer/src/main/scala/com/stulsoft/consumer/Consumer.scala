package com.stulsoft.consumer

import org.slf4j.{Logger, LoggerFactory}
import java.util.{Collections, Properties}

import org.apache.kafka.clients.consumer.KafkaConsumer

/**
  * @author Yuriy Stul.
  */
object Consumer extends App {
  val logger: Logger = LoggerFactory.getLogger(Consumer.getClass)
  logger.info("Started consumer")
  readMessages()

  /**
    * Reads messages
    */
  def readMessages(): Unit = {

    val props = new Properties
    props.put("bootstrap.servers", "localhost:9092,localhost:9093")
    props.put("group.id", "test")
    props.put("enable.auto.commit", "true")
    props.put("auto.commit.interval.ms", "1000")
    props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")
    props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")

    val consumer = new KafkaConsumer[String, String](props)
    consumer.subscribe(Collections.singletonList("myClusterTopic"))
    while (true) {
      val records = consumer.poll(100)
      records.forEach(record => {
        logger.info(s"offset = ${record.offset}, key = ${record.key}, value = ${record.value}")
      })
      consumer.commitSync()
    }

    //    consumer.unsubscribe()
    //    consumer.close()
  }
}
