package com.stulsoft.akka.data.watcher1.service

import com.typesafe.config.{Config, ConfigFactory}

/**
  * @author Yuriy Stul.
  */
object Settings{
  import scala.collection.JavaConverters._
  private val conf = ConfigFactory.load()

  /**
    * Return a list of sources (directories)
    * @return the list of sources (directories)
    */
  def sources:List[String] = conf.getStringList("sources").asScala.toList
}
