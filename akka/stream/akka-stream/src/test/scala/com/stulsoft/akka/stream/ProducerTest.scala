/*
 * Copyright (c) 2017. Yuriy Stul
 */

package com.stulsoft.akka.stream

/**
  * @author Yuriy Stul
  */

import com.stulsoft.akka.stream.Messages.{Line, Produce}

class ProducerTest extends MultiThreadedActorContext {

  val targetString = "THE COMPLETE SHERLOCK HOLMES"
  private val producer = system.actorOf(Producer.props(testActor))

  "Producer" must {
    "receive message Produce and send to testActor value of this line as string" in {
      producer ! Produce
      expectMsg(Line("", Some(producer)))
      producer ! Produce
      expectMsg(Line("", Some(producer)))
      producer ! Produce
      expectMsg(Line("", Some(producer)))
      producer ! Produce
      expectMsg(Line("", Some(producer)))
      producer ! Produce
      expectMsg(Line(targetString, Some(producer)))
    }
  }
}