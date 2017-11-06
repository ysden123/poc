/*
 * Copyright (c) 2017. Yuriy Stul
 */

package com.stulsoft.routing.group.router

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
    val system = ActorSystem("groupRouterSystem")
    system.actorOf(Props(new Actor1Creator(2)), "Creator") // Creates routee creator
    val router = system.actorOf(FromConfig.props(),"groupRouter") // Creates a group router

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
