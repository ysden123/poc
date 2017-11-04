/*
 * Copyright (c) 2017. Yuriy Stul
 */

package com.stulsoft.distributed.back.end

import akka.actor.{ActorSystem, Props}
import com.typesafe.config.ConfigFactory
import com.typesafe.scalalogging.LazyLogging

import scala.io.StdIn

/**
  * @author Yuriy Stul
  */
object Main extends App with LazyLogging {
  val config = ConfigFactory.load()
  val backendActorSystem = ActorSystem("backend", config)

  backendActorSystem.actorOf(Props[BackendActor],"backendActor")

  println("Enter any line to exit.")
  StdIn.readLine()
  backendActorSystem.terminate()
}
