/*
 * Copyright (c) 2017. Yuriy Stul
 */

package com.stulsoft.distributed.back.end

import akka.actor.{Actor, ActorLogging}

/**
  * @author Yuriy Stul
  */
class BackendActor extends Actor with ActorLogging {
  override def receive: Receive = {
    case m => log.info(s"BackendActor: received $m message")
  }
}
