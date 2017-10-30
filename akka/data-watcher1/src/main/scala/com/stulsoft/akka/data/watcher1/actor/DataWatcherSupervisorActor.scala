/*
 * Copyright (c) 2017. Yuriy Stul
 */

package com.stulsoft.akka.data.watcher1.actor

import akka.actor.SupervisorStrategy.Stop
import akka.actor.{Actor, ActorLogging, OneForOneStrategy, Props, SupervisorStrategy}
import com.stulsoft.akka.data.watcher1.Exceptions.DiskErrorException
import com.stulsoft.akka.data.watcher1.actor.DataWatcherSupervisorActor.StartDataWatcher

/**
  * Data watcher supervisor actor
  *
  * @author Yuriy Stul
  */
class DataWatcherSupervisorActor extends Actor with ActorLogging {

  override def supervisorStrategy: SupervisorStrategy = OneForOneStrategy() {
    case _: DiskErrorException => Stop
  }

  override def preStart(): Unit ={
    log.info("Started DataWatcherSupervisorActor")
    super.preStart()
  }

  override def receive: Receive = {
    case StartDataWatcher(path) =>
      context.actorOf(Props[DataWatcherActor]) // todo add path
  }
}

object DataWatcherSupervisorActor {

  case class StartDataWatcher(path: String)

}