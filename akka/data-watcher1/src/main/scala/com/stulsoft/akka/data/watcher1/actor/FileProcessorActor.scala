/*
 * Copyright (c) 2017. Yuriy Stul
 */

package com.stulsoft.akka.data.watcher1.actor

import akka.actor.SupervisorStrategy.Restart
import akka.actor.{Actor, ActorLogging, ActorRef, OneForOneStrategy, Props, SupervisorStrategy}
import com.stulsoft.akka.data.watcher1.Exceptions.DBConnectionException
import com.stulsoft.akka.data.watcher1.actor.DBRegistratorActor._
import com.stulsoft.akka.data.watcher1.actor.DataWatcherActor.NewFile

/** File processor actor
  *
  * @author Yuriy Stul
  */
class FileProcessorActor extends Actor with ActorLogging {
  var dbRegistratorActor: ActorRef = _

  override def preStart(): Unit = {
    log.info("Started FileProcessorActor")
    super.preStart()
    dbRegistratorActor = context.actorOf(Props[DBRegistratorActor])
  }

  override def supervisorStrategy: SupervisorStrategy = OneForOneStrategy() {
    case _: DBConnectionException => Restart
  }

  override def receive: Receive = {
    case NewFile(path, name) =>
      log.info(s"Processing $name file in the $path directory")
      dbRegistratorActor ! RegisterFile(path, name, 123)
  }
}
