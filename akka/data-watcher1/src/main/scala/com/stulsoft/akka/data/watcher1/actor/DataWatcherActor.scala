/*
 * Copyright (c) 2017. Yuriy Stul
 */

package com.stulsoft.akka.data.watcher1.actor

import akka.actor.{Actor, ActorLogging, Props}
import com.stulsoft.akka.data.watcher1.service.DirectoryWatcher

/**
  * Data watcher actor
  *
  * @author Yuriy Stul
  */
class DataWatcherActor(watcher: DirectoryWatcher) extends Actor with ActorLogging {

  override def preStart(): Unit = {
    super.preStart()
    watcher.watch(self)
  }

  override def receive: Receive = {
    case _ =>
  }
}

object DataWatcherActor {
  def props(watcher: DirectoryWatcher) = Props(new DataWatcherActor(watcher))
}