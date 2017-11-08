package com.stulsoft.fsm2

import akka.actor.{Actor, ActorLogging, ActorRef, FSM}
import scala.concurrent.duration._

/**
  * @author Yuriy Stul.
  */
class Inventory(publisher: ActorRef) extends Actor with FSM[State, StateData] with ActorLogging {
  var reserveId = 0
  startWith(WaitForRequests, StateData(0, Seq())) // defines initial state

  // Declare transition for state WaitForRequests
  when(WaitForRequests) {
    case Event(request: BookRequest, data: StateData) =>
      // Creates new state by appending new request
      val newStateData = data.copy(
        pendingRequests = data.pendingRequests :+ request
      )
      // Declares next state and updates StateData
      if (newStateData.nrBooksInStore > 0)
        goto(ProcessRequest) using newStateData
      else
        goto(WaitForPublisher) using newStateData

    case Event(PendingRequests, data: StateData) =>
      if (data.pendingRequests.isEmpty)
        stay // Uses stay when there aren't any pending requests
      else if (data.nrBooksInStore > 0)
        goto(ProcessRequest) // Uses goto without updating StateData
      else
        goto(WaitForPublisher) // Uses goto without updating StateData
  }

  whenUnhandled {
    // common code for all states
    case Event(request: BookRequest, data: StateData) =>
      stay using data.copy(pendingRequests = data.pendingRequests :+ request) // Only updates StateData
    case Event(e, s) =>
      log.warning("Received unhandled request {} in state {}/{}", e, stateName, s)
      stay
  }

  // Transition declaration of the state WaitForPublisher
  when(WaitForPublisher, stateTimeout = 5 seconds) { // Sets state timeout
    case Event(supply: BookSupply, data: StateData) =>
      goto(ProcessRequest) using data.copy(nrBooksInStore = supply.nrBooks)
    case Event(BookSupplySoldOut, _) =>
      goto(ProcessSoldOut)
    case Event(StateTimeout, _) =>
      goto(WaitForRequests) // Defines timeout transition
  }

  // Transition declaration of the state ProcessRequest
  when(ProcessRequest) {
    case Event(Done, data: StateData) =>
      goto(WaitForRequests) using data.copy(
        nrBooksInStore = data.nrBooksInStore - 1,
        pendingRequests = data.pendingRequests.tail
      )
  }

  // Transition declaration of the state SoldOut
  when(SoldOut) {
    case Event(request: BookRequest, _: StateData) =>
      goto(ProcessSoldOut) using StateData(0, Seq(request))
  }

  // Transition declaration of the state ProcessSoldOut
  when(ProcessSoldOut) {
    case Event(Done, _: StateData) =>
      goto(SoldOut) using StateData(0, Seq())
  }

  onTransition {
    // Entry action to check for pending requests
    case _ -> WaitForRequests =>
      if (nextStateData.pendingRequests.nonEmpty) {
        // go to next state
        self ! PendingRequests
      }
    // Entry action to send request to publisher
    case _ -> WaitForPublisher =>
      publisher ! PublisherRequest
    // Entry action to send a reply to sender and signal that processing is done
    case _ -> ProcessRequest =>
      val request = nextStateData.pendingRequests.head
      reserveId += 1
      request.target !
        BookReply(request.context, Right(reserveId))
      self ! Done
    // Entry action to send an error reply to all PendingRequests and signal that processing is done
    case _ -> ProcessSoldOut =>
      nextStateData.pendingRequests.foreach(request =>
        request.target ! BookReply(request.context, Left("SoldOut"))
      )
      self ! Done
  }
  initialize
}
