/*
 * Copyright (c) 2017. Yuriy Stul
 */

package com.stulsoft.akka.tdd

import akka.actor.Actor

class EchoActor extends Actor {
  override def receive: Receive = {
    case msg => sender() ! msg
  }
}
