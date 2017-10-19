package com.stulsoft.pakka.basics.general.example1.actors

import akka.actor.{Actor, Props}

/**
  * Created by Yuriy Stul on 10/3/2016.
  */
class HelloWorldActor extends Actor {
  override def preStart(): Unit = {
    println("==>HelloWorldActor.preStart")
    // create the greeter actor
    val greeter = context.actorOf(Props[GreeterActor], "greeter")

    // getting path of the HelloWorldActor actor
    println(s"HelloWorldActor.preStart: self.path (HelloWorldActor): ${self.path}")

    // getting path of the sender, GreeterActor actor
    println(s"HelloWorldActor.preStart: greeter.path (GreeterActor): ${greeter.path}")

    // Send it the 'Greet' message
    greeter ! GreeterMessages.Greet
  }

  override def receive: Receive = {
    // When we receive the 'Done' message, stop this actor
    // (which if this is still the initialActor will trigger the deathwatch and stop the entire ActorSystem)
    case GreeterMessages.Done =>
      println("==>HelloWorldActor.receive")
      context.stop(self)
  }
}
