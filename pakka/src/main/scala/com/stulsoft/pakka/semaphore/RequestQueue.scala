package com.stulsoft.pakka.semaphore

import akka.actor.ActorRef

import scala.collection.immutable.Queue

/**
  * Resource request
  *
  * Contains sender of a request.
  *
  * @param sender sender of the request.
  */
case class ResourceRequest(sender: ActorRef)

/**
  * Created by Yuriy Stul on 10/11/2016.
  *
  * Manages request queue
  */
class RequestQueue {
  private var queue: Queue[ResourceRequest] = Queue[ResourceRequest]()

  /**
    * Gets a resource request from queue
    *
    * @return the resource request, if exits
    */
  def get: Option[ResourceRequest] = {
    if (queue.isEmpty)
      None
    else {
      val (request: ResourceRequest, q: Queue[ResourceRequest]) = queue.dequeue
      queue = q
      Some(request)
    }
  }

  /**
    * Puts a resource request into queue
    *
    * @param request the resource request to put
    */
  def put(request: ResourceRequest): Unit = {
    require(request != null, "request is undefined")
    queue = queue.enqueue(request)
  }
}
