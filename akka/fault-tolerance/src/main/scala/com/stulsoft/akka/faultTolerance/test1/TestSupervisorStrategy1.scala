/*
 * Copyright (c) 2017. Yuriy Stul
 */

package com.stulsoft.akka.faultTolerance.test1


import akka.actor.SupervisorStrategy.Restart
import akka.actor._

import scala.concurrent.duration._

/**
  * @see [[https://gist.github.com/izmailoff/2ab46712cdd59df73218 Akka children/grandchildren restart example]]
  * @author Yuriy Stul
  */
class Supervisor extends Actor {

  override val supervisorStrategy:SupervisorStrategy =
    OneForOneStrategy(maxNrOfRetries = 10, withinTimeRange = 1 minute) {
      case _ =>
        println("RESTARTING PARENT FROM SUPERVISOR")
        Restart
    }
  val myChild:ActorRef = context.actorOf(Props[Parent])

  override def preStart:Unit =
    println("STARTED SUPERVISOR")

  override def postStop(): Unit =
    println("SUPERVISOR POST-STOP")

  def receive:Receive = {
    case "start" =>
      myChild ! "start"
    case "stop" =>
      println("STOPPING SUPERVISOR")
      context stop self
    case "throw" =>
      myChild ! "throw"
    case "forward" =>
      myChild ! 1
  }
}

class Parent extends Actor {

  override val supervisorStrategy:SupervisorStrategy =
    AllForOneStrategy(maxNrOfRetries = 10, withinTimeRange = 1 minute) {
      case _ =>
        println("STOPPING CHILDREN FROM PARENT")
        //Stop
        Restart
    }
  var children: Seq[ActorRef] = Seq.empty

  override def preStart:Unit =
    println("STARTED PARENT")

  override def preRestart(reason: Throwable, message: Option[Any]): Unit = ()

  override def postStop(): Unit =
    println("PARENT POST-STOP")

  def receive:Receive = {
    case "start" =>
      println("STARTING CHILDREN")
      children = 1 to 2 map { i =>
        context.actorOf(Props[Child], "child" + i)
      }
    case "stop" =>
      println("STOPPING PARENT (SELF)")
      context stop self
    case "throw" =>
      println("THROWING FROM PARENT")
      throw new IllegalStateException("Test exception")
    case v: Int =>
      children foreach (_ ! v)
  }
}

class Child extends Actor {

  override def preStart:Unit =
    println("STARTED CHILD " + toString)

  override def postStop(): Unit =
    println("CHILD POST-STOP")

  def receive:Receive = {
    case something =>
      println(s"CHILD $toString GOT: $something")
  }
}