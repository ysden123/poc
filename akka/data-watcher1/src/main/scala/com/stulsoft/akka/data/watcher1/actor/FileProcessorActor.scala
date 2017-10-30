/*
 * Copyright (c) 2017. Yuriy Stul
 */

package com.stulsoft.akka.data.watcher1.actor

import akka.actor.SupervisorStrategy.Restart
import akka.actor.{Actor, ActorLogging, OneForOneStrategy, SupervisorStrategy}
import com.stulsoft.akka.data.watcher1.Exceptions.DBConnectionException

/** File processor actor
  *
  * @author Yuriy Stul
  */
class FileProcessorActor extends Actor with ActorLogging {

  override def preStart(): Unit = {
    log.info("Started FileProcessorActor")
    super.preStart()
  }

  override def supervisorStrategy: SupervisorStrategy = OneForOneStrategy() {
    case _: DBConnectionException => Restart
  }

  override def receive: Receive = ???
}
