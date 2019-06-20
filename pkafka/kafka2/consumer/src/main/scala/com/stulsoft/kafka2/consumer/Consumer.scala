/*
 * Copyright (c) 2017. Yuriy Stul
 */

package com.stulsoft.kafka2.consumer

import java.util.{Collections, Properties}

import com.typesafe.scalalogging.LazyLogging
import org.apache.kafka.clients.consumer.KafkaConsumer

/**
  * @author Yuriy Stul
  */
object Consumer extends App with LazyLogging {
  logger.info("Started consumer")

  getMessages()

  def getMessages(): Unit = {
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
        val resultText = s"""Received message.\n\tPartition = ${record.partition()}, offset is ${record.offset}, topic is "${record.topic()}" key is "${record.key}", value is "${record.value}""""
        logger.info(resultText)
      })
      consumer.commitSync()
    }
  }

  logger.info("Finished consumer")
}
