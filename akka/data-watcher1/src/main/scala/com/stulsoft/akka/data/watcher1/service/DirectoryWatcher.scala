/*
 * Copyright (c) 2017. Yuriy Stul
 */

package com.stulsoft.akka.data.watcher1.service

import scala.concurrent.Future

/** Specifies a directory watcher service
  *
  * @author Yuriy Stul
  */
trait DirectoryWatcher {
  /**
    * Starts directory watch service
    *
    * @return Future
    */
  def watch(): Future[Unit]
}
