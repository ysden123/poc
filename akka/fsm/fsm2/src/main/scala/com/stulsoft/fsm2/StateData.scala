package com.stulsoft.fsm2

/**
  * @author Yuriy Stul.
  */
case class StateData(nrBooksInStore:Int, pendingRequests:Seq[BookRequest])
