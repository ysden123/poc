/*
 * Copyright (c) 2017. Yuriy Stul
 */

package com.stulsoft.akka.tdd

import akka.actor.{ActorSystem, Props}
import akka.testkit.{ImplicitSender, TestKit}
import org.scalatest.WordSpecLike

class LifeCycleHooksTest extends TestKit(ActorSystem("testsystem"))
  with WordSpecLike
  with ImplicitSender
  with StopSystemAfterAll {
  "A LifeCycleHooks Actor" must {
    "reflect all stages of actor" in {
      val testActorRef = system.actorOf(Props[LifeCycleHooks], "LifeCycleHooks") // <-- Starts actor
      testActorRef ! "restart" // <-- Restarts actor
      testActorRef.tell("msg", testActor)
      expectMsg("msg")
      system.stop(testActorRef) // <-- Stops actor
      Thread.sleep(1000)
    }
  }
}
