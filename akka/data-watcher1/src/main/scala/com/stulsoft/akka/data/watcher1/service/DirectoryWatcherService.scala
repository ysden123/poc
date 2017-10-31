/*
 * Copyright (c) 2017. Yuriy Stul
 */

package com.stulsoft.akka.data.watcher1.service

import java.nio.file.{FileSystems, Path, StandardWatchEventKinds}

import akka.actor.ActorRef
import com.stulsoft.akka.data.watcher1.actor.DataWatcherActor.NewFile
import com.typesafe.scalalogging.LazyLogging

import scala.concurrent.Future
import scala.util.{Failure, Success, Try}

/**
  * @author Yuriy Stul
  */
class DirectoryWatcherService(val path: Path) extends DirectoryWatcher with LazyLogging {
  /**
    * Starts directory watch service
    *
    * @param listener listener actor
    * @return Future
    */
  override def watch(listener: ActorRef): Future[Unit] = {
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
                sendMessage(listener, NewFile(path.toAbsolutePath.toString, event.context().toString))
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

  /**
    * Sends a message to a listener Actor
    *
    * @param listener the listener Actor
    * @param message  the  message
    */
  override def sendMessage(listener: ActorRef, message: Any): Unit = {
    listener ! message
  }
}
