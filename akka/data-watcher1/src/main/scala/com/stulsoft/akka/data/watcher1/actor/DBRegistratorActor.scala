/*
 * Copyright (c) 2017. Yuriy Stul
 */

package com.stulsoft.akka.data.watcher1.actor

import akka.actor.{Actor, ActorLogging}

/** DB registrator actor
  *
  * @author Yuriy Stul
  */
class DBRegistratorActor extends Actor with ActorLogging {

  override def preStart(): Unit ={
    log.info("Started DBRegistratorActor")
    super.preStart()
  }


  override def postRestart(reason: Throwable): Unit ={
    log.info(s"Restarted after ${reason.getMessage}")
    super.postRestart(reason)
  }

  override def receive: Receive = ???
}