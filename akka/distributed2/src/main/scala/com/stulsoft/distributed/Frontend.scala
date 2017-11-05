/*
 * Copyright (c) 2017. Yuriy Stul
 */

package com.stulsoft.distributed

import akka.actor.ActorSystem
import com.typesafe.config.ConfigFactory
import com.typesafe.scalalogging.LazyLogging

import scala.concurrent.Future

/**
  * @author Yuriy Stul
  */
object Frontend extends LazyLogging {
  var frontendActorSystem: ActorSystem = _

  import scala.concurrent.ExecutionContext.Implicits.global

  def start(): Future[Unit] = Future {
    logger.info("Starting frontend")
    if (frontendActorSystem == null) {
      val config = ConfigFactory.load("front-end.conf")

      try{
        frontendActorSystem = ActorSystem("frontend", config)
      }
      catch{
        case x: Throwable=>logger.error(x.getMessage)
      }
      val path = "akka.tcp://backend@0.0.0.0:2552/user/backendActor"
      val backendActor = frontendActorSystem.actorSelection(path)

      backendActor ! "Hello backend actor!"
    }
    logger.info("Started frontend")
  }

  def stop(): Unit = {
    logger.info("Stopping frontend")
    if (frontendActorSystem != null) {
      frontendActorSystem.terminate()
      frontendActorSystem = null
    }
  }
}

