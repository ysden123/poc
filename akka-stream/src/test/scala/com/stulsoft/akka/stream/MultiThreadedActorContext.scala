/*
 * Copyright (c) 2017. Yuriy Stul
 */

package com.stulsoft.akka.stream

import akka.actor.ActorSystem
import akka.testkit.TestKit
import org.scalatest.{BeforeAndAfterAll, MustMatchers, Suite, WordSpecLike}

class MultiThreadedActorContext extends TestKit(ActorSystem("question-test-app"))
  with WordSpecLike
  with MustMatchers
  with StopSystemAfterAll {

}

trait StopSystemAfterAll extends BeforeAndAfterAll {
  this: TestKit with Suite =>

  override protected def afterAll(): Unit = {
    super.afterAll()
    system.terminate()
  }
}