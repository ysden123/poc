/*
 * Copyright (c) 2017. Yuriy Stul
 */

package com.stulsoft.distributed

import akka.actor.ActorSystem
import com.typesafe.scalalogging.LazyLogging

import scala.concurrent.Future

/**
  * @author Yuriy Stul
  */
object Frontend extends LazyLogging {
  var frontendActorSystem: ActorSystem = _

  import scala.concurrent.ExecutionContext.Implicits.global

  def start(): Future[Unit] = Future {
    if (frontendActorSystem == null) {
      val config = Utils.readConfig("front-end.conf")

      frontendActorSystem = ActorSystem("frontend", config)
      val path = "akka.tcp://backend@0.0.0.0:2552/user/backendActor"
      val backendActor = frontendActorSystem.actorSelection(path)

      backendActor ! "Hello backend actor!"
    }
  }

  def stop(): Unit = {
    if (frontendActorSystem != null) {
      frontendActorSystem.terminate()
      frontendActorSystem = null
    }
  }
}

