/*
 * Copyright (c) 2017. Yuriy Stul
 */

package com.stulsoft.actor.strategy.emulator

import java.util.concurrent.TimeUnit

import com.stulsoft.actor.strategy.emulator.Actions.{Delay, Exception}
import com.typesafe.scalalogging.LazyLogging

import scala.concurrent.duration.Duration

/**
  * @author Yuriy Stul
  */
object Main extends App with LazyLogging {
  logger.info("Started")
  ActionManager.buildActions("test1", List("bbb","mmmm"))
  println(s"""action: ${ActionManager.nextAction("test1")}""")
  println(s"""action: ${ActionManager.nextAction("test1")}""")
  println(s"""action: ${ActionManager.nextAction("test1")}""")

  ActionManager.buildActions("test2", List(Delay(Duration(500,TimeUnit.MILLISECONDS)),Exception(new RuntimeException("test exception"))))
  println(s"""action: ${ActionManager.nextAction("test2")}""")
  println(s"""action: ${ActionManager.nextAction("test2")}""")
  println(s"""action: ${ActionManager.nextAction("test2")}""")

  logger.info("Completed")
}
