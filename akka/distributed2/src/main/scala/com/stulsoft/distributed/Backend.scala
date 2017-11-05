/*
 * Copyright (c) 2017. Yuriy Stul
 */

package com.stulsoft.distributed

import akka.actor.{ActorSystem, Props}
import com.typesafe.config.ConfigFactory
import com.typesafe.scalalogging.LazyLogging

import scala.concurrent.Future

/**
  * @author Yuriy Stul
  */
object Backend extends LazyLogging {
  var backendActorSystem: ActorSystem = _

  import scala.concurrent.ExecutionContext.Implicits.global

  def start(): Future[Unit] = Future {
    logger.info("Starting backend")
    if (backendActorSystem == null) {
      val config = ConfigFactory.load("back-end.conf")

      try {
        backendActorSystem = ActorSystem("backend", config)
      }
      catch {
        case x: Throwable => logger.error(x.getMessage)
      }

      backendActorSystem.actorOf(Props[BackendActor], "backendActor")
    }
    logger.info("Started backend")
  }

  def stop(): Unit = {
    logger.info("Stopping backend")
    if (backendActorSystem != null) {
      backendActorSystem.terminate()
      backendActorSystem = null
    }
  }
}
