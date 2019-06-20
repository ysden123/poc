/*
 * Copyright (c) 2019. Yuriy Stul
 */

package com.stulsoft.kafka.commit

import java.io.File

import com.typesafe.config.ConfigFactory

/** Application configuration
  *
  * @author Yuriy Stul
  */
object AppConfig {
  //  private lazy val config = ConfigFactory.parseFile(new File("app.conf"))
  //    .withFallback(ConfigFactory.load())
  private lazy val config = ConfigFactory.load("app.conf")

  private lazy val kafkaConfig = config.getConfig("kafka")

  def kafkaServers(): String = kafkaConfig.getString("servers")

  def kafkaAcks(): String = kafkaConfig.getString("acks")

  def kafkaGroupId(): String = kafkaConfig.getString("groupId")

  def kafkaTopic(): String = kafkaConfig.getString("topic")
}
