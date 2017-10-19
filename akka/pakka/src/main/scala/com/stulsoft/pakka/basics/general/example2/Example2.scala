package com.stulsoft.pakka.basics.general.example2

import akka.actor.{Actor, ActorLogging, ActorSystem, Props}
import akka.pattern.{ask, pipe}
import akka.util.Timeout

import scala.collection.mutable
import scala.concurrent.Await
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._

/**
  * Created by Yuriy Stul on 10/3/2016.
  */

case class SetRequest(key: String, value: String)

case class GetRequest(key: String)

case class GetResponse(key: Option[String])

class MapActor extends Actor with ActorLogging {
  val state: mutable.Map[String, String] = mutable.Map.empty

  def receive = {
    case r: SetRequest =>
      state += r.key -> r.value
    case r: GetRequest =>
      sender ! GetResponse(state.get(r.key))
    case r =>
      log.warning(s"Unexpected: $r")
  }
}

case object Start

class MainActor extends Actor with ActorLogging {
  implicit val timeout = Timeout(5.seconds)
  val mapActor = context.actorOf(Props(new MapActor), "mapActor")

  override def preStart() {
    self ! Start
  }

  def receive = {
    case Start =>
      mapActor ! "dummy request"
      mapActor ! SetRequest("key", "value")
      val respF = mapActor ? GetRequest("key")
      respF pipeTo self

    case r: GetResponse =>
      log.warning(s"Response: $r")
      context.system.terminate()
  }
}

object Example2 extends App {
  val system = ActorSystem("system")
//  system.logConfiguration()
  println(system.settings.config.getString("akka.actor.creation-timeout"))
  val mainActor = system.actorOf(Props(new MainActor), "mainActor")
  Await.result(system.whenTerminated, Duration.Inf)
}
