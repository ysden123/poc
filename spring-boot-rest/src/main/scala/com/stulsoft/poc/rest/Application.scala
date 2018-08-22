/*
 * Copyright (c) 2018. Yuriy Stul
 */

package com.stulsoft.poc.rest

import org.slf4j.LoggerFactory
import org.springframework.boot.SpringApplication

/**
  * @author Yuriy Stul
  */
object Application {
  private val logger = LoggerFactory.getLogger(Application.getClass)

  def main(args: Array[String]): Unit = {
    logger.info("==>main")
    val context = SpringApplication.run(classOf[ApplicationConfiguration], args: _*)
    logger.info("<==main")
  }
}
