package com.stulsoft.pakka.semaphore

import akka.actor.Actor
import com.typesafe.scalalogging.LazyLogging

/**
  * Created by Yuriy Stul on 10/12/2016.
  */

/**
  * The message to acquire a resource
  *
  */
case object AcquireMessage

/**
  * The message to release a resource
  *
  */
//case class ReleaseMessage(request: ResourceRequest)
case object ReleaseMessage

/**
  * The Semaphore Actor
  */
class SemaphoreActor(capacity: Int) extends Actor with LazyLogging {
  private val requestQueue = new RequestQueue
  private var available = capacity

  /**
    * Receives a messages
    *
    * @return Receive
    */
  override def receive: Receive = {
    case AcquireMessage =>
      logger.info("==>SemaphoreActor.AcquireMessage. available is {}", available)
      if (available > 0) {
        // answer
        sender ! true
        available -= 1
        logger.info("available = {}", available)
      } else {
        logger.info("Put to queue")
        requestQueue.put(ResourceRequest(sender))
      }
    case ReleaseMessage =>
      logger.info("release")
      sender ! true
      val resourceRequest = requestQueue.get
      resourceRequest match {
        case Some(r) =>
          available += 1
          r.sender ! true
        case None => logger.info("Queue is empty")
      }

    case _ => logger.info("uuu!!!")
  }
}
