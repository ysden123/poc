/*
 * Copyright (c) 2017. Yuriy Stul
 */

package com.stulsoft.akka.stream

import akka.actor.Actor

class ConsoleClient extends Actor {
  def receive:Actor.Receive = {
    case (x::xs) => println("Received data:")
      println((x::xs).mkString("\n"))
  }
}