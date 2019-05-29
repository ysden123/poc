/*
 * Copyright (c) 2019. Yuriy Stul 
 */

package com.stulsoft.akka.http.server

import com.typesafe.scalalogging.LazyLogging

/**
  * @author Yuriy Stul
  */
object Application extends App with LazyLogging {
  logger.info("==>Application")

//  val config = Configuration("sapBadResponse")
  val config = Configuration("sapGoodResponse")
  logger.info(s"Configuration: $config")

  Server(config)

}
