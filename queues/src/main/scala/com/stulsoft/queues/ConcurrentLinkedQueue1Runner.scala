/*
 * Copyright (c) 2018. Yuriy Stul
 */

package com.stulsoft.queues

import java.util.concurrent.atomic.AtomicInteger

import com.typesafe.scalalogging.LazyLogging

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, ExecutionContext, Future}
import scala.util.Random

/**
  * @author Yuriy Stul
  */
object ConcurrentLinkedQueue1Runner extends App with LazyLogging {
  test1()
  test2()

  def test1(): Unit = {
    logger.info("==>test1")
    implicit val ec = ExecutionContext.global
    val totalNumber = 10
    val atomicInteger = new AtomicInteger()

    Future {
      (1 to totalNumber).foreach(i => {
        logger.info(s"add $i")
        ConcurrentLinkedQueue1.add(SomeObject(i, s"text $i"))
        logger.info(s"Queue size is ${ConcurrentLinkedQueue1.size()}")
        Thread.sleep(100)
      })
    }

    val f2 = Future {
      val random = Random
      while (atomicInteger.get() < totalNumber) {
        val so = ConcurrentLinkedQueue1.get()
        if (so.isDefined) {
          logger.info(s"f2: ${so.get}")
          atomicInteger.incrementAndGet()
        } else {
          Thread.sleep(random.nextInt(500) + 1)
        }
      }
    }

    val f3 = Future {
      val random = Random
      while (atomicInteger.get() < totalNumber) {
        val so = ConcurrentLinkedQueue1.get()
        if (so.isDefined) {
          logger.info(s"f3: ${so.get}")
          atomicInteger.incrementAndGet()
        } else {
          Thread.sleep(random.nextInt(500) + 1)
        }
      }
    }

    Await.result(Future.sequence(List(f2, f3)), Duration.Inf)

    logger.info("<==test1")
  }

  def test2(): Unit = {
    logger.info("==>test2")
    implicit val ec = ExecutionContext.global
    val totalNumber = 1000
    val atomicInteger2 = new AtomicInteger()
    val atomicInteger3 = new AtomicInteger()
    val maxDelay = 10

    Future {
      val random = Random
      (1 to totalNumber).foreach(i => {
        ConcurrentLinkedQueue1.add(SomeObject(i, s"text $i"))
        Thread.sleep(random.nextInt(maxDelay) + 1)
      })
    }

    val f2 = Future {
      val random = Random
      while (atomicInteger2.get() + atomicInteger3.get() < totalNumber) {
        val so = ConcurrentLinkedQueue1.get()
        if (so.isDefined) {
          atomicInteger2.incrementAndGet()
        } else {
          Thread.sleep(random.nextInt(maxDelay) + 1)
        }
      }
    }

    val f3 = Future {
      val random = Random
      while (atomicInteger2.get() + atomicInteger3.get() < totalNumber) {
        val so = ConcurrentLinkedQueue1.get()
        if (so.isDefined) {
          atomicInteger3.incrementAndGet()
        } else {
          Thread.sleep(random.nextInt(maxDelay) + 1)
        }
      }
    }

    Await.result(Future.sequence(List(f2, f3)), Duration.Inf)

    logger.info(s"atomicInteger2 = ${atomicInteger2.get()}, atomicInteger3 = ${atomicInteger3.get()}")
    logger.info(s"totalNumber = $totalNumber, sum = ${atomicInteger2.get() + atomicInteger3.get()}")

    logger.info("<==test2")
  }
}
