package com.stulsoft.akka.data.watcher1.service

import java.nio.file.Paths

import com.stulsoft.akka.data.watcher1.service.TestEventGenerator.logger
import com.typesafe.scalalogging.LazyLogging

import scala.concurrent.Await
import scala.concurrent.duration.Duration

/**
  * @author Yuriy Stul.
  */
object Main extends App with LazyLogging {
  import DirectoryWatcher._

  logger.info("Creating source directories.")
  DataGenerator.createSourceDirectories(Settings.sources)

  logger.info("Starting watching")
  val w = watch(Paths.get(Settings.sources.head))

  TestEventGenerator.generate()

  Await.ready(w, Duration.Inf)

}
