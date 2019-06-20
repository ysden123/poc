package com.stulsoft.kafka3

import java.util.{Collections, Properties}

import com.typesafe.scalalogging.LazyLogging
import org.apache.kafka.clients.consumer.KafkaConsumer
import org.apache.kafka.common.TopicPartition

import scala.concurrent.Future

/** With manual offset setting
  * @author Yuriy Stul.
  */
final class Consumer2(val topic:String) extends LazyLogging {
  private var continueExecuting = false

  import scala.concurrent.ExecutionContext.Implicits.global

  def start(): Future[Unit] = Future {
    logger.info("Started Consumer2")
    continueExecuting = true

    val props = new Properties
    props.put("bootstrap.servers", "localhost:9092,localhost:9093")
    props.put("group.id", "test")
//    props.put("enable.auto.commit", "true")
    props.put("enable.auto.commit", "false")
    props.put("auto.offset.reset","earliest")
    props.put("auto.commit.interval.ms", "1000")
    props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")
    props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")

    val consumer = new KafkaConsumer[String, String](props)
    consumer.subscribe(Collections.singletonList(topic))


    val partition = new TopicPartition(topic,0)
//    consumer.seek(partition, 62)
//    consumer.seekToBeginning(Collections.singletonList(partition))
    while (continueExecuting) {
      val records = consumer.poll(100)
      records.forEach(record => {
        val resultText = s"""Received message.\n\tPartition = ${record.partition()}, offset is ${record.offset}, topic is "${record.topic()}" key is "${record.key}", value is "${record.value}""""
        logger.info(resultText)
      })
//      consumer.commitSync()
    }

    logger.info("Stopped Consumer2")
  }

  def stop(): Unit = {
    logger.info("Stopping Consumer2")
    continueExecuting = false
  }
}
