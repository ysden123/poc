/*
 * Copyright (c) 2017. Yuriy Stul
 */

package com.stulsoft.akka.data.watcher1.service

import java.nio.file.{FileSystems, Path, Paths, StandardWatchEventKinds}

import com.typesafe.scalalogging.LazyLogging

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}
import scala.util.{Failure, Success, Try}

/**
  * @author Yuriy Stul
  */
case class DirectoryWatcher(path: Paths)

object DirectoryWatcher extends LazyLogging {

  def watch(path: Path): Future[Unit] = {
    logger.debug(s"watch(): entering, path=$path")
    val watchService = FileSystems.getDefault.newWatchService

    path.register(
      watchService,
      StandardWatchEventKinds.ENTRY_CREATE
    )

    import scala.concurrent.ExecutionContext.Implicits.global
    Future {
      import collection.JavaConverters._
      var loop = true
      while (loop) {
        Try(watchService.take) match {
          case Success(key) =>
            key.pollEvents.asScala foreach {
              event =>
                logger.info("New file {}", event.context())
            }
            if (!key.reset) {
              logger.warn("watch(): reset unsuccessful, exiting the loop")
              loop = false
            }
          case Failure(_) => // Ignore
        }
      }
      logger.debug("end")
    }
  }
}