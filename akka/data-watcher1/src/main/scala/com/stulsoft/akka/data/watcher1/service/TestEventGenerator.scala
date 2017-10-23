package com.stulsoft.akka.data.watcher1.service

import com.typesafe.scalalogging.LazyLogging

/**
  * Generates test events
  *
  * @author Yuriy Stul.
  */
object TestEventGenerator extends LazyLogging {
  /**
    * Generates test events
    */
  def generate(): Unit = {
    logger.info("Started test events generating.")

    Thread.sleep(1000)
    var dir = Settings.sources.head
    var fn = "test11.txt"
    logger.info(s"Writing test data into $dir/$fn")
    DataGenerator.generateTextFile(dir,fn,100)

    Thread.sleep(500)
    dir = Settings.sources.head
    fn = "test12.txt"
    logger.info(s"Writing test data into $dir/$fn")
    DataGenerator.generateTextFile(dir,fn,100)

    Thread.sleep(500)
    dir = Settings.sources(1)
    fn = "test21.txt"
    logger.info(s"Writing test data into $dir/$fn")
    DataGenerator.generateTextFile(dir,fn,100)

    Thread.sleep(500)
    dir = Settings.sources(1)
    fn = "test22.txt"
    logger.info(s"Writing test data into $dir/$fn")
    DataGenerator.generateTextFile(dir, fn, 100)

    logger.info("Stopped test events generating.")
  }
}
