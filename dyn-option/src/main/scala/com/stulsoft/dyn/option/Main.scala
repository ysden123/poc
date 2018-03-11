/*
 * Copyright (c) 2018. Yuriy Stul
 */

package com.stulsoft.dyn.option

import scala.collection.JavaConverters._

/** See README.md
  *
  * @author Yuriy Stul
  */
object Main extends App {
  println("Args:")
  args.foreach(println)

  println("")
  println("System environment variables (only started with my-prefix):")
  System.getProperties.asScala
    .filter(i => i._1.startsWith("my"))
    .toList
    .sortBy((i) => i._1)
    .foreach(i => println(s"name=${i._1}, value=${i._2}"))
}
