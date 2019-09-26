/*
 * Copyright (c) 2019. Yuriy Stul
 */

package com.stulsoft.zeebe.app1

import java.io.File

import com.typesafe.config.ConfigFactory

/**
 * @author Yuriy Stul
 */
object AppConfig {
  private lazy val config = ConfigFactory.parseFile(new File("application.conf"))
    .withFallback(ConfigFactory.load())

  def zeebeHost: String = config.getConfig("zeebe").getString("host")
  def zeebePort: Int = config.getConfig("zeebe").getInt("port")
}
