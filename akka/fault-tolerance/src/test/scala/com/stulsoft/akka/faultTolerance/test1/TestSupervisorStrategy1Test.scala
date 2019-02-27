/*
 * Copyright (c) 2017. Yuriy Stul
 */

package com.stulsoft.akka.faultTolerance.test1

import akka.actor.ActorSystem
import akka.testkit.{TestActorRef, TestKit}
import com.stulsoft.akka.faultTolerance.StopSystemAfterAll
import org.scalatest.{MustMatchers, WordSpecLike}

/**
  * @author Yuriy Stul
  */
class TestSupervisorStrategy1Test extends TestKit(ActorSystem("testsystem"))
  with WordSpecLike
  with MustMatchers
  with StopSystemAfterAll {

  "SupervisorStrategy1" must {
    "handle exceptions" in {
      println("starting supervisor")
      val supervisor = TestActorRef[Supervisor]

      println(s"""*** akka.actor.guardian-supervisor-strategy: ${System.getenv("akka.actor.guardian-supervisor-strategy")}""")

      println("starting parent and it's children")
      supervisor ! "start"
      println("just waiting for everything to start\n\n")
      Thread.sleep(2000)

      println("sending children a message")
      supervisor ! "forward"
      println("waiting for message to be received")
      Thread.sleep(2000)

      println("making parent throw an exception")
      supervisor ! "throw" // parent throws an exception now, should get restarted
      println("waiting for actors to restart\n\n")
      Thread.sleep(2000)

      println("sending children a message")
      supervisor ! "forward"
      println("waiting for message to be received")
      Thread.sleep(2000)

      println("shutting down the system")
    }
  }
}
