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
    if (backendActorSystem == null) {
      val config = Utils.readConfig("back-end.conf")

      backendActorSystem = ActorSystem("backend", config)

      backendActorSystem.actorOf(Props[BackendActor], "backendActor")
    }
  }

  def stop(): Unit = {
    if (backendActorSystem != null) {
      backendActorSystem.terminate()
      backendActorSystem = null
    }
  }
}
