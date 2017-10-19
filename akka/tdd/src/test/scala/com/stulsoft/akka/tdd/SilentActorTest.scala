/*
 * Copyright (c) 2017. Yuriy Stul
 */

package com.stulsoft.akka.tdd

import akka.actor.{ActorSystem, Props}
import akka.testkit.{TestActorRef, TestKit}
import com.stulsoft.akka.tdd.SilentActor._
import org.scalatest.{MustMatchers, WordSpecLike}

/**
  * @see Akka in Action by Raymond Roestenburg, Rob Bakker and Rob Williams
  * @author Yuriy Stul
  */
class SilentActorTest extends TestKit(ActorSystem("testsystem"))
  with WordSpecLike
  with MustMatchers
  with StopSystemAfterAll {
  "A Silent Actor" must {
    "change state when it receives a message, single threaded" in {
      // Write the test, first fail
      val silentActor = TestActorRef[SilentActor]
      silentActor ! SilentMessage("whisper")
      silentActor.underlyingActor.state must contain("whisper")
    }

    "change state when it receives a message, multi-threaded" in {
      //Write the test, first fail
      val silentActor = system.actorOf(Props[SilentActor], "s3")
      silentActor ! SilentMessage("whisper1")
      silentActor ! SilentMessage("whisper2")
      silentActor ! GetState(testActor)
      expectMsg(Vector("whisper1", "whisper2"))
    }
  }
}