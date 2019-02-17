/*
 * Copyright (c) 2019. Yuriy Stul
 */

package com.stulsoft.scala.test.flat.spec

import java.io.File

import com.typesafe.config.ConfigFactory
import com.typesafe.scalalogging.LazyLogging

/**
  * @author Yuriy Stul
  */
object Config extends LazyLogging {
  private var config = ConfigFactory.parseFile(new File("application.conf"))
    .withFallback(ConfigFactory.load())

  /** Reinitializes a configuration.
    *
    * Use for Unit Testing
    * {{{
    *   System.setProperty("config.resource", "test.application.conf")
    *   Config.reinitialize()
    * }}}
    *
    * @param configResourceFileName specifies the name of the configuration file
    */
  def reinitialize(configResourceFileName: String): Unit = {
    config = ConfigFactory.load(configResourceFileName)
  }

  def parameter: String = config.getConfig("test").getString("parameter")
}
