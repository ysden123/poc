/*
 * Copyright (c) 2019. Yuriy Stul
 */

package com.stulsoft.kafka.commit

import java.time.Duration
import java.util.{Collections, Properties}

import org.apache.kafka.clients.consumer.KafkaConsumer

/** Consumer
  *
  * @param resetMode  specifies offset rest mode ("earliest" or "latest")
  * @param autoCommit specifies auto commit mode ("true" or "false")
  * @param commit     specifies whether commit or do not commit after processing a messages
  * @author Yuriy Stul
  */
case class Consumer(resetMode: String, autoCommit: String, commit: Boolean) {
  def readMessages(): Unit = {
    val props = new Properties
    props.put("bootstrap.servers", AppConfig.kafkaServers())
    props.put("group.id", AppConfig.kafkaGroupId())
    props.put("enable.auto.commit", autoCommit)
    props.put("auto.commit.interval.ms", "1000")
    props.put("auto.offset.reset", resetMode)
    props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")
    props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")

    val consumer = new KafkaConsumer[String, String](props)
    consumer.subscribe(Collections.singletonList(AppConfig.kafkaTopic()))
    while (true) {
      try {
        val records = consumer.poll(Duration.ofMillis(100))
        records.forEach(record => {
          val resultText = s"""Received message.\n\tPartition = ${record.partition()}, offset is ${record.offset}, topic is "${record.topic()}" key is "${record.key}", value is "${record.value}""""
          println(resultText)
        })
        if (commit)
          consumer.commitSync()
      } catch {
        case e: Exception => println(s"Failed read messages: ${e.getMessage}")
      }
    }
  }
}
