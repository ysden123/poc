package com.stulsoft.pakka.basics.general.example1.actors

import akka.actor.{Actor, ActorLogging}

/**
  * Created by Yuriy Stul on 10/3/2016.
  */

// Note: Usually the message object (GreeterMessages)
// and the actor class (GreeterActor) will be called the same thing (eg. Greeter)
object GreeterMessages {
  case object Greet
  case object Done
}

class GreeterActor extends Actor with ActorLogging {
  def receive = {
    case GreeterMessages.Greet =>
      // getting path of the GreeterActor actor
      println(s"GreeterActor.receive: self.path (GreeterActor): ${self.path}")

      // getting path of the sender, GreeterActor actor
      println(s"GreeterActor.receive: sender.path (HelloWorldActor): ${sender.path}")

      val greetMsg = "Hello World!"
      println(s"GreeterActor.receive: greetMsg: $greetMsg")
      log.info(greetMsg)
      sender() ! GreeterMessages.Done // Send the 'Done' message back to the sender
  }
}