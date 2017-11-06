/*
 * Copyright (c) 2017. Yuriy Stul
 */

package com.stulsoft.routing.group.router

import akka.actor.{Actor, ActorLogging}

import scala.util.Random

/**
  * @author Yuriy Stul
  */
class Actor1 extends Actor with ActorLogging {
  private val n = self.path.name
  override def receive: Receive = {
    case x =>
      log.info(s"Actor1 ($n): received $x")
      Thread.sleep(Random.nextInt(1000))
      log.info(s"Actor1 ($n): completed msg handling")
  }

  override def preStart(): Unit = {
    super.preStart()
    log.info(s"Staring Actor1 ($n)")
  }
}
