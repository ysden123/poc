/*
 * Copyright (c) 2019. Yuriy Stul
 */

package com.stulsoft.kafka.admin

import java.time.Duration
import java.util.Properties

import com.typesafe.scalalogging.LazyLogging
import org.apache.kafka.clients.consumer.KafkaConsumer

/**
  * @author Yuriy Stul
  */
class AdminConsumer(val servers: String) extends LazyLogging {
  val props = new Properties
  props.put("bootstrap.servers", servers)
  props.put("group.id", "admin_consumer")
  props.put("enable.auto.commit", "false")
  props.put("auto.offset.reset", "earliest")
  props.put("auto.commit.interval.ms", "1000")
  props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")
  props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")

  def calculateNumberOfMessages(topic: String): Int = {
    try {
      val consumer = new KafkaConsumer[String, String](props)
      consumer.subscribe(java.util.Collections.singleton(topic))
      val records = consumer.poll(Duration.ofMillis(10000))
      records.count()
    } catch {
      case e: Exception =>
        logger.error(s"Error: ${e.getMessage}")
        -1
    }
  }
}
