package com.stulsoft.akka.data.watcher1.service

import java.nio.file.Paths

import com.typesafe.scalalogging.LazyLogging

import scala.concurrent.Await
import scala.concurrent.duration.Duration

/** Main entry point
  *
  * @author Yuriy Stul.
  */
object Main extends App with LazyLogging {

  import DirectoryWatcher._

  logger.info("Creating source directories.")
  DataGenerator.createSourceDirectories(Settings.sources)

  Thread.sleep(500)

  val watchers = Settings.sources.map(source => {
    logger.info(s"Starting watching $source")
    watch(Paths.get(source))
  })

  TestEventGenerator.generate()

  watchers.foreach(watcher => Await.ready(watcher, Duration.Inf))
}
