/*
 * Copyright (c) 2017. Yuriy Stul
 */

package com.stulsoft.akka.stream

import akka.actor.ActorRef

/**
  * @author Yuriy Stul
  */
object Messages {

  case class Line(text: String, producer: Option[ActorRef])

  case class ShouldProduce(out: ActorRef)

  case class StartProducingQuestions(producer: Option[ActorRef])

  case class GetConsumer()

  case object Produce

  case object EndOfFileStream

  case object Load

  case object StopProducingQuestions

}
