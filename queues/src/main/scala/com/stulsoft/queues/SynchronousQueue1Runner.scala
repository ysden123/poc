/*
 * Copyright (c) 2018. Yuriy Stul
 */

package com.stulsoft.queues

import java.util.concurrent.atomic.AtomicInteger

import com.typesafe.scalalogging.LazyLogging

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, ExecutionContext, Future, _}
import scala.util.Random

/**
  * @author Yuriy Stul
  */
object SynchronousQueue1Runner extends App with LazyLogging {
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
        SynchronousQueue1.add(SomeObject(i, s"text $i"))
        logger.info(s"Queue size is ${SynchronousQueue1.size()}")
        blocking {
          Thread.sleep(100)
        }
      })
    }

    val f2 = Future {
      val random = Random
      while (atomicInteger.get() < totalNumber) {
        val so = SynchronousQueue1.get()
        if (so.isDefined) {
          logger.info(s"f2: ${so.get}")
          atomicInteger.incrementAndGet()
        } else {
          blocking {
            Thread.sleep(random.nextInt(500) + 1)
          }
        }
      }
    }

    val f3 = Future {
      val random = Random
      while (atomicInteger.get() < totalNumber) {
        val so = SynchronousQueue1.get()
        if (so.isDefined) {
          logger.info(s"f3: ${so.get}")
          atomicInteger.incrementAndGet()
        } else {
          blocking {
            Thread.sleep(random.nextInt(500) + 1)
          }
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
    val atomicInteger4 = new AtomicInteger()
    val maxDelay = 10

    Future {
      val random = Random
      (1 to totalNumber).foreach(i => {
        blocking {
          Thread.sleep(random.nextInt(maxDelay) + 1)
        }
        SynchronousQueue1.add(SomeObject(i, s"text $i"))
      })
      logger.info("Completed adding")
    }

    val f2 = Future {
      val random = Random
      while (atomicInteger2.get() + atomicInteger3.get() + atomicInteger4.get() < totalNumber) {
        val so = SynchronousQueue1.get()
        if (so.isDefined) {
          atomicInteger2.incrementAndGet()
          blocking {
            Thread.sleep(random.nextInt(maxDelay / 2) + 1)
          }
        } else {
          blocking {
            Thread.sleep(random.nextInt(maxDelay) + 1)
          }
        }
      }
      logger.info("f2 completed")
    }

    val f3 = Future {
      val random = Random
      while (atomicInteger2.get() + atomicInteger3.get() + atomicInteger4.get() < totalNumber) {
        val so = SynchronousQueue1.get()
        if (so.isDefined) {
          atomicInteger3.incrementAndGet()
          blocking {
            Thread.sleep(random.nextInt(maxDelay / 2) + 1)
          }
        } else {
          blocking {
            Thread.sleep(random.nextInt(maxDelay) + 1)
          }
        }
      }
      logger.info("f3 completed")
    }

    val f4 = Future {
      val random = Random
      while (atomicInteger2.get() + atomicInteger3.get() + atomicInteger4.get() < totalNumber) {
        val so = SynchronousQueue1.get()
        if (so.isDefined) {
          atomicInteger4.incrementAndGet()
          blocking {
            Thread.sleep(random.nextInt(maxDelay / 2) + 1)
          }
        } else {
          blocking {
            Thread.sleep(random.nextInt(maxDelay) + 1)
          }
        }
      }
      logger.info("f4 completed")
    }

    Await.result(Future.sequence(List(f2, f3)), Duration.Inf)

    logger.info(s"atomicInteger2 = ${atomicInteger2.get()}, atomicInteger3 = ${atomicInteger3.get()}, atomicInteger4 = ${atomicInteger4.get()}")
    logger.info(s"totalNumber = $totalNumber, sum = ${atomicInteger2.get() + atomicInteger3.get() + atomicInteger4.get()}")

    logger.info("<==test2")
  }
}
