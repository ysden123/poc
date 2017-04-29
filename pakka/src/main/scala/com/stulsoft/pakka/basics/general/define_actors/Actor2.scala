package com.stulsoft.pakka.basics.general.define_actors

import akka.actor.{Actor, ActorSystem, Props}

/**
  * Created by Yuriy Stul on 10/3/2016.
  *
  * Example of an Actor with companion object
  */
class Actor2(magicNumber: Int) extends Actor {
  override def receive: Receive = {
    case x: Int => sender() ! (x + magicNumber)
  }
}

// Companion object
object Actor2 {

  /**
    * Create Props for an actor of this type
    *
    * @param magicNumber The magic number to be passed to this actor’s constructor
    * @return a Props for creating this actor, which can then be further configured
    *         (e.g. calling ‘.withDispatcher()‘ on it)
    */
  def props(magicNumber: Int): Props = Props(new Actor2(magicNumber))
}

/**
  * Example creating Actor2 actor
  */
object ForTestActor2{
  def main(args: Array[String]): Unit = {
    val system = ActorSystem("mySystem")
    val actor2 = system.actorOf(Actor2.props(13), "actor2")
  }
}