/*
 * Copyright (c) 2017. Yuriy Stul
 */

package com.stulsoft.mailgun4s

import com.typesafe.config.ConfigFactory

/**
  * Mailgun configuration
  *
  * The Mailgun API key should be stored into system variable with name "MG_API_KEY"
  * The Mailgun domain should be stored into system variable with name "MG_DOMAIN"
  *
  * @author Yuriy Stul
  */
class Config {
  private lazy val conf = ConfigFactory.load().getConfig("mailgun")

  val apiKey: String = conf.getString("apiKey")
  val domain: String = conf.getString("domain")
  val urlToSendMessage: String = conf.getString("urlToSendMessage")
  val apiVersion: String = conf.getString("apiVersion")
}


