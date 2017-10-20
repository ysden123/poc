/*
 * Copyright (c) 2017. Yuriy Stul
 */

package com.stulsoft.akka.tdd.sideeffectingactor

import akka.actor.{Actor, ActorLogging, ActorRef, Props}

case class Greeting02(message: String)

/**
  * In many situations, it’s easier to adapt the code a
  * bit so that it’s easier to test. Clearly, if we pass the listeners to the class under test, we
  * don’t have to do any configuration or filtering; we’ll simply get each message our
  * Actor under test produces.
  *
  * Constructor takes an optional listener; default set to None
  *
  * @param listener optional listener actor
  */
class Greeter02(listener: Option[ActorRef]) extends Actor with ActorLogging {
  def receive: Receive = {
    case Greeting02(who) =>
      val message = "Hello " + who + "!"
      log.info(message)
      listener.foreach(_ ! message) // Optionally sends to the listener
  }
}

object Greeter02 {
  def props(listener: Option[ActorRef] = None) = Props(new Greeter02(listener))
}