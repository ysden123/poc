package com.stulsoft.cluster1

import akka.actor.{Actor, ActorLogging, ActorSystem, Props}
import akka.cluster.Cluster
import akka.routing.FromConfig
import com.typesafe.config.ConfigFactory

import scala.concurrent.{Await, Future}
import scala.io.StdIn
/**
  * @see [[https://gist.github.com/johanandren/d2b874e59320e07f84a4 Simple sample with clustered round robin pool]]
  * @author Yuriy Stul.
  */
object ClusteredRoundRobinPool extends App {

  val commonConfig = ConfigFactory.parseString(
    """
      |akka {
      |  loglevel = "DEBUG"
      |  loggers = ["akka.event.slf4j.Slf4jLogger"]
      |  actor {
      |    provider = "akka.cluster.ClusterActorRefProvider"
      |    deployment {
      |      /echo {
      |        router = round-robin-pool
      |        cluster {
      |          enabled = on
      |          max-nr-of-instances-per-node = 3
      |          allow-local-routees = off
      |        }
      |      }
      |    }
      |  }
      |  remote {
      |    netty.tcp {
      |      hostname = "127.0.0.1"
      |     }
      |  }
      |  cluster {
      |    seed-nodes = ["akka.tcp://cluster@127.0.0.1:2551"]
      |    metrics.enabled = off
      |  }
      |}
    """.stripMargin)

  implicit val system1 = ActorSystem("cluster", ConfigFactory.parseString(
    """
      |akka.remote.netty.tcp.port = 2551
    """.stripMargin).withFallback(commonConfig))

  implicit val system2 = ActorSystem("cluster", ConfigFactory.parseString(
    """
      |akka.remote.netty.tcp.port = 2552
    """.stripMargin).withFallback(commonConfig))

  implicit val system3 = ActorSystem("cluster", ConfigFactory.parseString(
    """
      |akka.remote.netty.tcp.port = 2553
    """.stripMargin).withFallback(commonConfig))



  while (Cluster(system1).state.members.size != 3) {
    Thread.sleep(250)
  }

  class EchoActor extends Actor with ActorLogging {
    log.info("Starting")
    override def receive: Receive = {
      case "ping" =>
        log.info("ping-pong")
        sender() ! "pong"
    }
  }

  val pool = system1.actorOf(FromConfig.props(Props[EchoActor]), "echo")


  import scala.concurrent.ExecutionContext.Implicits.global
  import scala.concurrent.duration._
  system1.scheduler.schedule(1.second, 1.second, pool, "ping")

/*
  println("Enter to quit")
  StdIn.readLine()
*/

  Thread.sleep(1000 * 5)

  Await.result(Future.traverse(List(system1, system2, system3))(system => system.terminate()), Duration.Inf)

}
