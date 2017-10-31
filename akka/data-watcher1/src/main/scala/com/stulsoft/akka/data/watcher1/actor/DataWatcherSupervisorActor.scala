/*
 * Copyright (c) 2017. Yuriy Stul
 */

package com.stulsoft.akka.data.watcher1.actor

import java.nio.file.Paths

import akka.actor.SupervisorStrategy.Stop
import akka.actor.{Actor, ActorLogging, OneForOneStrategy, SupervisorStrategy}
import com.stulsoft.akka.data.watcher1.Exceptions.DiskErrorException
import com.stulsoft.akka.data.watcher1.actor.DataWatcherSupervisorActor.StartDataWatcher
import com.stulsoft.akka.data.watcher1.service.DirectoryWatcherService

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
      log.info(s"Started data watcher for $path")
      val dataWatcher = new DirectoryWatcherService(Paths.get(path))
      context.actorOf(DataWatcherActor.props(dataWatcher))
  }
}

object DataWatcherSupervisorActor {

  case class StartDataWatcher(path: String)

}