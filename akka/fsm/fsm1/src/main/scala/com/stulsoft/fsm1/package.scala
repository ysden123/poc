package com.stulsoft

import akka.actor.ActorRef

/** Finite State Machine
  *
  * Based on book "Akka in Action".
  *
  * Variant without timer.
  *
  * @author Yuriy Stul.
  */
package object fsm1 {

  // Events
  case class BookRequest(context: AnyRef, target: ActorRef)

  case class BookSupply(nrBooks: Int)

  case object BookSupplySoldOut

  case object Done

  case object PendingRequests

  //responses
  case object PublisherRequest

  case class BookReply(context: AnyRef, reserveId: Either[String, Int])

  // states
  sealed trait State
  case object WaitForRequests extends State
  case object ProcessRequest extends State
  case object WaitForPublisher extends State
  case object SoldOut extends State
  case object ProcessSoldOut extends State

}
