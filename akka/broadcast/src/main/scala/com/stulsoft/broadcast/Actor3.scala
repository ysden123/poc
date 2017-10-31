/*
 * Copyright (c) 2017. Yuriy Stul
 */

package com.stulsoft.broadcast

import akka.actor.{Actor, ActorLogging}

/**
  * @author Yuriy Stul.
  */
class Actor3 extends Actor with ActorLogging {
  override def preStart(): Unit = {
    super.preStart()
    log.info("Starting Actor3")
  }

  override def receive: Receive = {
    case x => log.info(s"Actor3: received $x")
  }
}
