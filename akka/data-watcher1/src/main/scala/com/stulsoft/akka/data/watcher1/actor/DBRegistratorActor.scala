/*
 * Copyright (c) 2017. Yuriy Stul
 */

package com.stulsoft.akka.data.watcher1.actor

import akka.actor.{Actor, ActorLogging}
import com.stulsoft.akka.data.watcher1.actor.DBRegistratorActor._

/** DB registrator actor
  *
  * @author Yuriy Stul
  */
class DBRegistratorActor extends Actor with ActorLogging {

  override def preStart(): Unit = {
    log.info("Started DBRegistratorActor")
    super.preStart()
  }

  override def postRestart(reason: Throwable): Unit = {
    log.info(s"Restarted after ${reason.getMessage}")
    super.postRestart(reason)
  }

  override def receive: Receive = {
    case RegisterFile(path: String, name: String, numberOfLines: Int) =>
      log.info(s"Registering $name file in the $path with $numberOfLines lines")
  }
}

object DBRegistratorActor {

  case class RegisterFile(path: String, name: String, numberOfLines: Int)

}