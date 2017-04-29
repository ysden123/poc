/*
 * Copyright (c) 2016. Yuriy Stul
 */

package com.stulsoft.pspark.stream

import java.io.File

import org.apache.spark.SparkConf
import org.apache.spark.streaming.{Seconds, StreamingContext}

/**
  * Created by Yuriy Stul on 11/27/2016.
  */
object Util {
  /**
    * Returns an absolute path to file
    *
    * @param name specifies resource file; may include subdirectory
    * @return the absolute path to file
    */
  def getResourceFilePath(name: String): String = new File(getClass.getClassLoader.getResource(name).toURI)
    .getAbsolutePath

  /**
    * Gets a Streaming Context
    *
    * @param appName the application name
    * @param master  the master, e.g. "local[*]"
    * @return the Streaming Context
    */
  def getStreamingContext(appName: String, master: String): StreamingContext = {
    val conf = new SparkConf()
      .setAppName(appName)
      .setMaster(master)
    val stream = new StreamingContext(conf, Seconds(5))
    stream
  }
}
