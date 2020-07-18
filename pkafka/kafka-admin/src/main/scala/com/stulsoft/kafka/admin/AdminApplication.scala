/*
 * Copyright (c) 2019. Yuriy Stul
 */

package com.stulsoft.kafka.admin

import java.util.Properties

import com.typesafe.scalalogging.StrictLogging
import org.apache.kafka.clients.admin.AdminClient

import scala.collection.JavaConverters._
import scala.io.StdIn


/**
 * @author Yuriy Stul
 */
object AdminApplication extends App with StrictLogging {
  logger.info("==>AdminApplication")


  println("Enter servers (host:port[,host1:port1]")
  val servers = StdIn.readLine()
  val adminConsumer = new AdminConsumer(servers)

  val admin = AdminClient.create(buildConfig())

  listMessageCountPerTopic()
  listGroups()

  admin.close()
  logger.info("<==AdminApplication")

  def listMessageCountPerTopic(): Unit = {
    logger.info("==>listMessageCountPerTopic")
    admin
      .listTopics()
      .listings()
      .get()
      .asScala
      .foreach(topicListing => {
        logger.info(s"${topicListing.name()} topic has ${adminConsumer.calculateNumberOfMessages(topicListing.name())} messages.")
      })
  }

  def listGroups():Unit={
    logger.info("==>listGroups")
    admin
      .listConsumerGroups()
      .all()
      .get()
      .asScala
      .foreach(consumerGroupListing =>{
        logger.info(s"${consumerGroupListing}")
      })
  }

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
