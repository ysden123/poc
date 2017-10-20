/*
 * Copyright (c) 2017. Yuriy Stul
 */

package com.stulsoft.akka.tdd.sideeffectingactor

import akka.actor.{Actor, ActorLogging}

case class Greeting01(message: String)

class Greeter01 extends Actor with ActorLogging {
  def receive: Receive = {
    case Greeting01(message) => log.info("Hello {}!", message)
  }
}