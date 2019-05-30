/*
 * Copyright (c) 2019. Yuriy Stul 
 */

package com.stulsoft.akka.http.server

import java.io.File

import com.typesafe.scalalogging.LazyLogging

/**
  * @author Yuriy Stul
  */
object Application extends App with LazyLogging {
  logger.info("==>Application")

  val fileNames = new File("src/main/resources/")
    .listFiles()
    .toStream
    .toList
    .filter(file => file.isFile && file.getName.endsWith(".conf"))
    .map(file => file.getName)
    .zipWithIndex
    .map { case (name: String, index: Int) => (index, name) }
    .toMap

  println("Please enter number of configuration file or empty line to exit")

  fileNames.foreach(e => println(s"${e._1} - ${e._2}"))
  var number = Console.in.readLine()
  if (number.isEmpty)
    System.exit(0)
  val fileNameNumber = number.toInt

  if (fileNameNumber < 0 || fileNameNumber > (fileNames.size - 1)) {
    println("Invalid number")
    System.exit(1)
  }

  val config = Configuration(fileNames(fileNameNumber))

  logger.info(s"Configuration: $config")

  Server(config)

}
