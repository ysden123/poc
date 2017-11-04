/*
 * Copyright (c) 2017. Yuriy Stul
 */

package com.stulsoft.distributed

import com.typesafe.config.ConfigFactory
import com.typesafe.scalalogging.LazyLogging

import scala.concurrent.Future
import scala.io.StdIn

/**
  * @author Yuriy Stul
  */
object Main extends App with LazyLogging {

  val backend:Future[Unit] = Backend.start()
  val frontend:Future[Unit] = Frontend.start()

  println("Enter a line to exit")
  StdIn.readLine()

  Frontend.stop()
  Backend.stop()
}
