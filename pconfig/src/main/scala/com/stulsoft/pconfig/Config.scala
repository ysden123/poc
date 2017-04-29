/*
 * Copyright (c) 2017. Yuriy Stul
 */

package com.stulsoft.pconfig

import java.io.File

import com.typesafe.config.ConfigFactory

/**
  * @author Yuriy Stul
  */
object Config {
  private lazy val test = ConfigFactory.parseFile(new File("application.conf")).withFallback(ConfigFactory.load()).getConfig("test")
  val p1: String = test.getString("p1")
  val p2: Int = test.getInt("p2")
}
