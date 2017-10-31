/*
 * Copyright (c) 2017. Yuriy Stul
 */

package com.stulsoft.akka.tdd

import akka.actor.{Actor, ActorLogging}

object LifeCycleHooks {
  var lastMessage: String = _
}

class LifeCycleHooks extends Actor
  with ActorLogging {
  System.out.println("Constructor")

  override def preStart(): Unit = {
    println("preStart")
  }

  override def postStop(): Unit = {
    println("postStop")
  }

  override def preRestart(reason: Throwable, message: Option[Any]): Unit = {
    println("preRestart")
    message.foreach(
      m => {
        LifeCycleHooks.lastMessage = m.toString
        println(s"preRestart, lastMessage is ${LifeCycleHooks.lastMessage}")
      }
    )
    super.preRestart(reason, message)
  }

  override def postRestart(reason: Throwable): Unit = {
    println(s"postRestart, lastMessage was ${LifeCycleHooks.lastMessage}")
    super.postRestart(reason)
  }

  def receive: Receive = {
    case "restart" =>
      throw new IllegalStateException("force restart")
    case msg: AnyRef =>
      println("Receive")
      sender() ! msg
  }
}
