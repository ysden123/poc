/*
 * Copyright (c) 2017. Yuriy Stul
 */

package com.stulsoft.mailgun4s

import org.scalatest.{FlatSpec, Matchers}

/**
  * Unit tests for Config class
  *
  * @author Yuriy Stul
  */
class ConfigTest extends FlatSpec with Matchers {
  behavior of "Config"

  "constructor" should "create new instance of the Config" in {
    val config = new Config
    config should not be null
  }

  "apiKey" should "return a string value" in {
    val config = new Config
    config.apiKey shouldBe a[String]
    config.apiKey.length should be > 0
  }

  "domain" should "return a string value" in {
    val config = new Config
    config.domain shouldBe a[String]
    config.domain.length should be > 0
  }

  "urlToSendMessage" should "return a string value" in {
    val config = new Config
    config.urlToSendMessage shouldBe a[String]
    config.urlToSendMessage.length should be > 0
  }

  "apiVersion" should "return a string value" in {
    val config = new Config
    config.apiVersion shouldBe a[String]
    config.apiVersion.length should be > 0
  }
}
