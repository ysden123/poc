/*
 * Copyright (c) 2017. Yuriy Stul
 */

package com.stulsoft.akka.data.watcher1.service

import akka.actor.ActorRef

import scala.concurrent.Future

/** Specifies a directory watcher service
  *
  * @author Yuriy Stul
  */
trait DirectoryWatcher {
  /**
    * Starts directory watch service
    *
    * @param listener listener actor
    * @return Future
    */
  def watch(listener: ActorRef): Future[Unit]
}
