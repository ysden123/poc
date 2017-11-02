package com.stulsoft.pakka.supervisor.strategy

import akka.actor.SupervisorStrategy.{Directive, Escalate, Restart, Resume, Stop}
import akka.actor.{Actor, ActorLogging, ActorRef, ActorSystem, OneForOneStrategy, Props, SupervisorStrategy}
import com.typesafe.scalalogging.LazyLogging

/** Playing with supervisor strategy
  *
  * @author Yuriy Stul.
  */
object StratMain1 extends App with LazyLogging {
  val testMsg = "test message"
  val exceptionMsg = "exception message"

  testRunner(Stop)
  testRunner(Resume)
  testRunner(Restart)
  testRunner(Escalate)

  /**
    * Supervisor Actor.
    *
    * Run Actor2.
    *
    * @param directive specifies a strategy
    */
  class Actor1(directive: Directive) extends Actor with ActorLogging {

    var actor2: ActorRef = _

    override def supervisorStrategy: SupervisorStrategy = OneForOneStrategy() {
      case _: RuntimeException => directive
    }

    override def preStart(): Unit = {
      super.preStart()
      log.info("Actor1: preStart")
      actor2 = context.actorOf(Props[Actor2])
    }

    override def preRestart(reason: Throwable, message: Option[Any]): Unit = {
      super.preRestart(reason, message)
      log.info("Actor1: preRestart")
    }

    override def receive: Receive = {
      case x =>
        log.info(s"Actor1: received: $x")
        actor2 ! x
    }
  }

  class Actor2 extends Actor with ActorLogging {

    override def preStart(): Unit = {
      super.preStart()
      log.info("Actor2: preStart")
    }

    override def preRestart(reason: Throwable, message: Option[Any]): Unit = {
      log.info("Actor2: preRestart")
      super.preRestart(reason, message)
    }

    override def receive: Receive = {
      case `testMsg` =>
        log.info(s"Actor1: processing: $testMsg")
      case `exceptionMsg` =>
        log.info(s"Actor1: processing: $exceptionMsg")
        throw new RuntimeException("test exception")
    }
  }

  def testRunner(directive: Directive): Unit = {
    logger.info("Started testRunner")
    logger.info(s"directive is $directive")
    val system = ActorSystem("test-app")
    val a1 = system.actorOf(Props(new Actor1(directive)))
    a1 ! testMsg
    Thread.sleep(500)
    a1 ! exceptionMsg
    Thread.sleep(1000)
    system.terminate()
    logger.info("Finished testRunner")
  }
}
