/*
 * Copyright (c) 2017. Yuriy Stul
 */

package com.stulsoft.akka.tdd

import akka.actor.{ActorSystem, Props}
import akka.testkit.{ImplicitSender, TestKit}
import org.scalatest.WordSpecLike

class EchoActorTest extends TestKit(ActorSystem("testsystem"))
  with WordSpecLike
  with ImplicitSender
  with StopSystemAfterAll {
  "An Echo Actor" must {
    "reply with the same message it receives without ask" in {
      val echo = system.actorOf(Props[EchoActor], "echo2")
      val someMessage = "some message"
      echo ! someMessage
      expectMsg(someMessage)
    }
  }
}
