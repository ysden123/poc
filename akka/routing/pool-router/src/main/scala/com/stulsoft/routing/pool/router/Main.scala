/*
 * Copyright (c) 2017. Yuriy Stul
 */

package com.stulsoft.routing.pool.router

import akka.actor.{ActorSystem, Props}
import akka.routing.FromConfig
import com.typesafe.scalalogging.LazyLogging

import scala.io.StdIn

/**
  * @author Yuriy Stul
  */
object Main extends App with LazyLogging {
  start()

  def start(): Unit = {
    logger.info("Started Main")
    val system = ActorSystem("poolRouterSystem")
    // @formatter:off
    val router = system.actorOf(            // Defines router using configuration
      FromConfig.props(Props(new Actor1)),  // How router should create routees
      "poolRouter"                          // Name of router (see conf file)
    )
    // @formatter:on

    router ! "msg 1"
    router ! "msg 2"
    router ! "msg 3"
    router ! "msg 4"
    router ! "msg 5"
    router ! "msg 6"

    println("Enter line to exit")
    StdIn.readLine()
    system.terminate()
    logger.info("Finished Main")
  }
}
