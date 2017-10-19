package com.stulsoft.pakka.semaphore

import akka.actor.{ActorSystem, Props}
import akka.util.Timeout
import akka.pattern.ask
import com.typesafe.scalalogging.LazyLogging

import scala.concurrent.{Await, Future, TimeoutException}
import scala.concurrent.duration._
import scala.util.{Failure, Success, Try}

/**
  * Created by Yuriy Stul on 10/12/2016.
  */

/**
  * The Semaphore class.
  *
  * @param capacity   the semaphore capacity
  * @param maxTimeout timeout value
  */
class Semaphore(capacity: Int, maxTimeout: FiniteDuration) extends LazyLogging {
  require(capacity > 0, "Zero or negative capacity")
  private val system = ActorSystem("system")
  private val semaphoreActor = system.actorOf(Props(new SemaphoreActor(capacity)), "semaphoreActor")

  /**
    * Acquires a resource.
    * @return Success(Unit), if available resource exists; otherwise - Failure(TimeoutException)
    */
  def acquire(): Try[Unit] = {
    implicit val timeout = Timeout(maxTimeout)
    implicit val ec = system.dispatcher
    logger.info("==>Semaphore.acquire")
    val future: Future[Boolean] = ask(semaphoreActor, AcquireMessage).mapTo[Boolean]
    try {
      val result = Await.result(future, maxTimeout)
      logger.info("result is {}", result)
      Success(Unit) // OK
    } catch {
      case e: TimeoutException =>
        logger.error("Timeout exception. {}", e.getMessage)
        Failure(e)
    }
  }

  /**
    * Releases a resource
    */
  def release(): Unit = {
    //    implicit val timeout = Timeout(1.seconds)
    logger.info("==>Semaphore.release")
    semaphoreActor ! ReleaseMessage
  }
}

/**
  * Companion object for Semaphore
  */
object Semaphore {
  /**
    * Creates new instance of the Semaphore class.
    *
    * @param capacity the semaphore capacity
    * @param timeout  timeout value
    * @return new instance of the Semaphore class.
    */
  def apply(capacity: Int, timeout: FiniteDuration): Semaphore = new Semaphore(capacity, timeout)
}
