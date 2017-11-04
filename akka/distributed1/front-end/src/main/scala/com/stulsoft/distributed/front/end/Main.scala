/*
 * Copyright (c) 2017. Yuriy Stul
 */

package com.stulsoft.distributed.front.end

import akka.actor.ActorSystem
import com.typesafe.config.ConfigFactory
import com.typesafe.scalalogging.LazyLogging

import scala.io.StdIn

/**
  * @author Yuriy Stul
  */
object Main extends App with LazyLogging {
  val config = ConfigFactory.load()
  val frontendActorSystem = ActorSystem("frontend", config)

  val path = "akka.tcp://backend@0.0.0.0:2552/user/backendActor"
  val backendActor = frontendActorSystem.actorSelection(path)

  backendActor ! "Hello backend actor!"

  println("Enter any line to exit.")
  StdIn.readLine()
  frontendActorSystem.terminate()
}
