/*
 * Copyright (c) 2019. Yuriy Stul
 */

package com.stulsoft.kafka.commit

import java.util.{Calendar, Properties}
import java.util.concurrent.TimeUnit

import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}

/** Produces a messages
  *
  * @author Yuriy Stul
  */
object Producer extends App {
  sendMessages()

  def sendMessages(): Unit = {
    println("==>sendMessages")
    val props = new Properties()
    props.put("bootstrap.servers", AppConfig.kafkaServers())
    props.put("acks", AppConfig.kafkaAcks())
    props.put("retries", Int.box(0))
    props.put("batch.size", Int.box(16384))
    props.put("linger.ms", Int.box(1))
    props.put("buffer.memory", Int.box(33554432))
    props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer")
    props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer")
    val producer = new KafkaProducer[String, String](props)

    for (number <- 1 to 3) {
      sendMessage(producer, number)
    }

    println("<==sendMessages")
  }

  private def sendMessage(producer: KafkaProducer[String, String], number: Int): Unit = {
    try {
      val future = producer.send(new ProducerRecord[String,
        String](AppConfig.kafkaTopic(),
        "ysTestKey",
        buildMessage(number)))
      try {
        val result = future.get(10, TimeUnit.SECONDS)
        val resultText = s"Succeeded send message. Offset is ${result.offset()}, partition is ${result.partition()}, topic is ${result.topic()}"
        println(resultText)
      } catch {
        case e: Exception => println(s"Failed send message. Error: ${e.getMessage}")
      }
    } catch {
      case e: Throwable => println(s"Failed sending message with error ${e.getMessage}")
    }
  }

  private def buildMessage(number: Int): String = s"$number ${Calendar.getInstance().getTime.toString}"
}
