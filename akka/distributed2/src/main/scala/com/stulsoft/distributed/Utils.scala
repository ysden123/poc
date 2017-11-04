/*
 * Copyright (c) 2017. Yuriy Stul
 */

package com.stulsoft.distributed

import com.typesafe.config.{Config, ConfigFactory}

/**
  * @author Yuriy Stul
  */
object Utils {
  def readConfig(name: String): Config = {
    getClass.getClassLoader.getResourceAsStream(name) match {
      case null =>
        ConfigFactory.load(name)
      case _ =>
        ConfigFactory.parseResourcesAnySyntax(name)
    }
  }
}
