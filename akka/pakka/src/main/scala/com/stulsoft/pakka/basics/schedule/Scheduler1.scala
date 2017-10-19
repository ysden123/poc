/*
 * Copyright (c) 2016. Yuriy Stul
 */

package com.stulsoft.pakka.basics.schedule

import akka.actor.ActorSystem
import com.typesafe.scalalogging.LazyLogging

import scala.concurrent.duration._
import scala.concurrent.{Await, ExecutionContext, Future, Promise}
import scala.util.Success

/**
  * Playing with scheduling
  *
  * See [[http://stackoverflow.com/questions/16359849/scala-scheduledfuture]]
  * Created by Yuriy Stul on 12/2/2016.
  */
object Scheduler1 extends App with LazyLogging {
  logger.info("main: start")
  val system = ActorSystem("Schedule1")

  /**
    * Example of a function to execute a code (block) with delay
    *
    * @param delay    the delay
    * @param block    th code to execute
    * @param executor the execution context
    * @tparam T result type
    * @return Future with T type
    */
  def delayedFuture[T](delay: FiniteDuration)(block: => T)(implicit executor: ExecutionContext): Future[T] = {
    val promise = Promise[T]
    system.scheduler.scheduleOnce(delay) {
      try {
        val result = block
        promise.complete(Success(result))
      } catch {
        case t: Throwable => promise.failure(t)
      }
    }
    promise.future
  }

  import scala.concurrent.ExecutionContext.Implicits.global

  val done1 = delayedFuture(5.seconds)(logger.info("Hey1"))
  val result1 = Await.result(done1, 6.seconds)
  logger.info(s"result is $result1")

  val done2 = delayedFuture(5.seconds)({
    logger.info("Hey2")
    "Hello from Hey 2"
  })
  val result2 = Await.result(done2, 6.seconds)
  logger.info(s"result is $result2")

  //////////////////////////////////////////////
  //  Using akka 'after' pattern
  //////////////////////////////////////////////
  import akka.pattern.after

  val done3 = after(5.seconds, system.scheduler)(Future {
    logger.info("Hey3")
  })
  val result3 = Await.result(done3, 6.seconds)
  logger.info(s"result is $result3")

  val done4 = after(5.seconds, system.scheduler)(Future {
    logger.info("Hey4")
    "Hello from Hey 4"
  })
  val result4 = Await.result(done4, 6.seconds)
  logger.info(s"result is $result4")

  system.terminate()

  logger.info("main: end")
}
