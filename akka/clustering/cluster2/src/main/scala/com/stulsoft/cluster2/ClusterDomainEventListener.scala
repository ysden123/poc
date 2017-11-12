/*
 * Copyright (c) 2017. Yuriy Stul
 */

package com.stulsoft.cluster2

import akka.actor.{Actor, ActorLogging}
import akka.cluster.ClusterEvent._
import akka.cluster.{Cluster, MemberStatus}

/** Cluster event listener
  *
  * @author Yuriy Stul
  */
class ClusterDomainEventListener extends Actor with ActorLogging {
  // Subscribes to the cluster domain events on actor creation
  Cluster(context.system).subscribe(self, classOf[ClusterDomainEvent])

  // Listens for cluster domain events
  override def receive: Receive = {
    case MemberUp(member) => log.info(s"$member UP.")
    case MemberExited(member) => log.info(s"$member EXITED.")
    case MemberRemoved(m, previousState) =>
      if (previousState == MemberStatus.Exiting) {
        log.info(s"Member $m gracefully exited, REMOVED.")
      } else {
        log.info(s"$m downed after unreachable, REMOVED.")
      }
    case UnreachableMember(m) => log.info(s"$m UNREACHABLE")
    case ReachableMember(m) => log.info(s"$m REACHABLE")
    case s: CurrentClusterState => log.info(s"cluster state: $s")
  }

  override def postStop(): Unit = {
    // Unsubscribes after actor is stopped
    Cluster(context.system).unsubscribe(self)
    super.postStop()
  }
}
