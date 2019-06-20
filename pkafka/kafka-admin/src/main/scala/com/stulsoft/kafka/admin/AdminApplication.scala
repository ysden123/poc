/*
 * Copyright (c) 2019. Yuriy Stul
 */

package com.stulsoft.kafka.admin

import java.util
import java.util.Properties

import com.typesafe.scalalogging.LazyLogging
import org.apache.kafka.clients.admin.AdminClient

import collection.JavaConverters._
import scala.io.StdIn


/**
  * @author Yuriy Stul
  */
object AdminApplication extends App with LazyLogging {
  logger.info("==>AdminApplication")


  println("Enter servers (host:port[,host1:port1]")
  val servers =  StdIn.readLine()
  val adminConsumer = new AdminConsumer(servers)

  val admin = AdminClient.create(buildConfig())
  admin
    .listTopics()
    .listings()
    .get()
    .asScala
    .foreach(topicListing => {
      logger.info(s"${topicListing.name()} topic has ${adminConsumer.calculateNumberOfMessages(topicListing.name())} messages.")
    })

  admin.close()
  logger.info("<==AdminApplication")

  def buildConfig(): Properties = {
    val props = new Properties()
    props.put("bootstrap.servers", servers)
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
