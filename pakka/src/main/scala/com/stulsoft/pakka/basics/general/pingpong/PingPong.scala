package com.stulsoft.pakka.basics.general.pingpong

import akka.actor.{Actor, ActorRef, ActorSystem, Props}

/**
  * Created by Yuriy Stul on 10/7/2016.
  *
  * See original http://alvinalexander.com/scala/scala-akka-actors-ping-pong-simple-example
  */

case object PingMessage
case object PongMessage
case object StartMessage
case object StopMessage

/**
  * An Akka Actor example written by Alvin Alexander of
  * http://devdaily.com
  *
  * Shared here under the terms of the Creative Commons
  * Attribution Share-Alike License: http://creativecommons.org/licenses/by-sa/2.5/
  *
  * more akka info: http://doc.akka.io/docs/akka/snapshot/scala/actors.html
  */
class Ping(pong: ActorRef) extends Actor {
  var count = 0
  def incrementAndPrint() { count += 1; println("ping") }
  def receive = {
    case StartMessage =>
      incrementAndPrint()
      pong ! PingMessage
    case PongMessage =>
      incrementAndPrint()
      if (count > 99) {
        sender ! StopMessage
        println("ping stopped")
        context.stop(self)
      } else {
        sender ! PingMessage
      }
  }
}

class Pong extends Actor {
  def receive = {
    case PingMessage =>
      println("  pong")
      sender ! PongMessage
    case StopMessage =>
      println("pong stopped")
      context.stop(self)
  }
}

object PingPongTest extends App {
  val system = ActorSystem("PingPongSystem")
  val pong = system.actorOf(Props[Pong], name = "pong")
  val ping = system.actorOf(Props(new Ping(pong)), name = "ping")
  // start them going
  ping ! StartMessage
}