package com.stulsoft.pakka.semaphore

import akka.actor.{ActorSystem, Props}
import org.scalatest.{FlatSpec, Matchers}


/**
  * Created by Yuriy Stul on 10/12/2016.
  */
class SemaphoreActorTest extends FlatSpec with Matchers {
  behavior of "SemaphoreActor"
  "create instance" should "initialize RequestQueue" in {
    val system = ActorSystem("system")
    val semaphoreActor = system.actorOf(Props(new SemaphoreActor(5)), "semaphoreActor")
    Thread.sleep(500)
    system.stop(semaphoreActor)
  }
}
