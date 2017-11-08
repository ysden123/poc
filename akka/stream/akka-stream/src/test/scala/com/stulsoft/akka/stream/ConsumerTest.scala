/*
 * Copyright (c) 2017. Yuriy Stul
 */

package com.stulsoft.akka.stream

import akka.testkit.TestProbe
import com.stulsoft.akka.stream.Messages.{EndOfFileStream, Load, StartProducingQuestions, StopProducingQuestions}

class ConsumerTest extends MultiThreadedActorContext {

  val parentActor = TestProbe()
  private val consumer = parentActor.childActorOf(Consumer.props(testActor))

  "Consumer" must {
    "send StartProducingQuestions to parent actor" in {
      consumer ! Load
      parentActor.expectMsg(StartProducingQuestions(None))
    }

    "receive string and save it to its state, and send StartProducingQuestions" in {
      consumer ! "hi?"
      expectNoMsg()
      parentActor.expectNoMsg()
    }

    "receive string 10 times, and send StopProducingQuestions then " +
      "wait for 2 sec and send StartProducingQuestions again" in {
      for (i <- 0 until 10) {
        consumer ! "hi?"
      }
      consumer ! "hi?"
      expectMsg(List.fill(10)("hi?"))
      parentActor.expectMsg(StopProducingQuestions)
      parentActor.expectMsg(StartProducingQuestions(None))

    }

    "receive EndOfFileStream and send to parentActor StopProducingQuestion" in {
      consumer ! EndOfFileStream
    }
  }
}