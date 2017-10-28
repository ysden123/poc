package com.stulsoft.akka.data.watcher1.service

import com.typesafe.scalalogging.LazyLogging

/** Main entry point
  *
  * @author Yuriy Stul.
  */
object Main extends App with LazyLogging {
  /*
    logger.info("Creating source directories.")
    DataGenerator.createSourceDirectories(Settings.sources)

    Thread.sleep(500)

    val watchers = Settings.sources.map(source => {
      logger.info(s"Starting watching $source")
      new DirectoryWatcherService(Paths.get(source)).watch()
    })

    TestEventGenerator.generate()

    watchers.foreach(watcher => Await.ready(watcher, Duration.Inf))
  */
}
