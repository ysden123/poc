/*
 * Copyright (c) 2017. Yuriy Stul
 */

package com.stulsoft.kafka3

import java.util.{Collections, Properties}

import com.stulsoft.kafka3.AutoCommit.AutoCommit
import com.stulsoft.kafka3.AutoOffsetRest.AutoOffsetRest
import com.stulsoft.kafka3.Commit.Commit
import com.typesafe.scalalogging.LazyLogging
import org.apache.kafka.clients.consumer.KafkaConsumer

import scala.concurrent.Future

/**
  * @author Yuriy Stul
  */
final class Consumer(val groupId: String, val enabledAutoCommit: AutoCommit, val autoOffsetRest: AutoOffsetRest,
                     val commit: Commit, val topic: String) extends LazyLogging {
  private var continueExecuting = false

  import scala.concurrent.ExecutionContext.Implicits.global

  def start(): Future[Unit] = Future {
    logger.info("Started Consumer")
    continueExecuting = true

    try {
      val props = new Properties
      props.put("bootstrap.servers", KAFKA_HOSTS)
      props.put("group.id", groupId)
      props.put("enable.auto.commit", enabledAutoCommit.toString)
      props.put("auto.offset.reset", autoOffsetRest.toString)
      props.put("auto.commit.interval.ms", "1000")
      props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")
      props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")

      val consumer = new KafkaConsumer[String, String](props)

      consumer.subscribe(Collections.singletonList(topic))
      while (continueExecuting) {
        val records = consumer.poll(100)
        records.forEach(record => {
          val resultText = s"""Received message.\n\tPartition = ${record.partition()}, offset is ${record.offset}, topic is "${record.topic()}" key is "${record.key}", value is "${record.value}""""
          logger.info(resultText)
        })
        commit match {
          case _: Commit.Commit => consumer.commitSync()
        }

      }

      consumer.close()
    }
    catch {
      case e: Exception => e.printStackTrace()
    }
    logger.info("Stopped Consumer")
  }

  def stop(): Unit = {
    logger.info("Stopping Consumer")
    continueExecuting = false
  }
}
