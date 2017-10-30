/*
 * Copyright (c) 2017. Yuriy Stul
 */

package com.stulsoft.akka.data.watcher1.actor

import akka.actor.SupervisorStrategy.Resume
import akka.actor.{Actor, ActorLogging, ActorRef, OneForOneStrategy, Props, SupervisorStrategy}
import com.stulsoft.akka.data.watcher1.Exceptions.FileCorruptException
import com.stulsoft.akka.data.watcher1.actor.DataWatcherActor.NewFile
import com.stulsoft.akka.data.watcher1.service.DirectoryWatcher

/**
  * Data watcher actor
  *
  * @author Yuriy Stul
  */
class DataWatcherActor(watcher: DirectoryWatcher) extends Actor with ActorLogging {
  log.info("Created DataWatcherActor")

  var fileProcessorActor: ActorRef = _

  override def supervisorStrategy: SupervisorStrategy = OneForOneStrategy() {
    case _: FileCorruptException => Resume
  }

  override def preStart(): Unit = {
    log.info("Started DataWatcherActor")
    super.preStart()
    fileProcessorActor = context.actorOf(Props[FileProcessorActor])
    watcher.watch(self)
  }

  override def receive: Receive = {
    case NewFile(path, name) =>
      log.info(s"Created $name file in $path")
      fileProcessorActor ! NewFile(path, name)
  }
}

object DataWatcherActor {
  def props(watcher: DirectoryWatcher) = Props(new DataWatcherActor(watcher))

  //
  // Messages
  //
  case class NewFile(path: String, name: String)

}