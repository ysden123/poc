/*
 * Copyright (c) 2019. Yuriy Stul
 */

package com.stulsoft.kafka.admin

import java.util
import java.util.Properties

import com.typesafe.scalalogging.LazyLogging
import org.apache.kafka.clients.admin.AdminClient

import collection.JavaConverters._


/**
  * @author Yuriy Stul
  */
object AdminApplication extends App with LazyLogging {
  logger.info("==>AdminApplication")

  val adminConsumer = new AdminConsumer

  val admin = AdminClient.create(buildConfig())
  admin
    .listTopics()
    .listings()
    .get()
    .asScala
    .foreach(topicListing => {
      logger.info(s"Topic name: ${topicListing.name()}, number of messages is ${adminConsumer.calculateNumberOfMessages(topicListing.name())}")
    })

  admin.close()
  logger.info("<==AdminApplication")

  def buildConfig(): Properties = {
    val props = new Properties()
    props.put("bootstrap.servers", "localhost:9092")
    props.put("acks", "all")
    props.put("retries", 0)
    props.put("batch.size", 16384)
    props.put("linger.ms", 1)
    props.put("buffer.memory", 33554432)
    props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer")
    props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer")

    props
  }
}
