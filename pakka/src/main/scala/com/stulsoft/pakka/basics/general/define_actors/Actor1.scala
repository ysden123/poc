/*
 * Copyright (c) 2016. Yuriy Stul
 */

package com.stulsoft.pakka.basics.general.define_actors

import akka.actor.Actor
import akka.event.Logging

/**
  * Created by Yuriy Stul on 10/3/2016.
  */
class Actor1 extends Actor {
  val log = Logging(context.system, this)

  override def receive: Receive = {
    case "test" => log.info("received test")
    case _ => log.info("received unknown message")
  }
}
