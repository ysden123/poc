/*
 * Copyright (c) 2017. Yuriy Stul
 */

package com.stulsoft.cluster2

import akka.actor._
import akka.cluster.Cluster
import com.typesafe.config._
import com.typesafe.scalalogging.LazyLogging

import scala.io.StdIn

/**
  * @author Yuriy Stul
  */
object Main extends App with LazyLogging {
  start()

  def start(): Unit = {
    logger.info("Start")
    val seedConfig = ConfigFactory.load("seed")
    val seedSystem = ActorSystem("words", seedConfig)

    StdIn.readLine()
    // Leaving cluster
    val address = Cluster(seedSystem).selfAddress
    Cluster(seedSystem).leave(address)

    seedSystem.terminate()

    logger.info("Finish")
  }
}
