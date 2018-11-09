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
object Queue2Runner extends App with LazyLogging {
  test1()
  test2()
  test3()

  def test1(): Unit = {
    println("==>test1")
    (1 to 10).foreach(i => Queue2.add(SomeObject(i, s"text $i")))

    var someObject: Option[SomeObject] = Queue2.get()
    while (someObject.isDefined) {
      println(someObject)
      someObject = Queue2.get()
    }

    println("<==test1")
  }

  def test2(): Unit = {
    println("==>test2")
    implicit val ec = ExecutionContext.global
    val totalNumber = 10

    val f1 = Future {
      (1 to totalNumber).foreach(i => {
        Queue2.add(SomeObject(i, s"text $i"))
        Thread.sleep(1000)
      })
    }

    val f2 = Future {
      var count = 0
      while (count < totalNumber) {
        val so = Queue2.get()
        if (so.isDefined) {
          println(so.get)
          count += 1
        } else {
          Thread.sleep(500)
        }
      }
    }

    Await.result(f2, Duration.Inf)

    println("<==test2")
  }

  def test3(): Unit = {
    logger.info("==>test3")
    implicit val ec = ExecutionContext.global
    val totalNumber = 10
    val atomicInteger = new AtomicInteger()

    Future {
      (1 to totalNumber).foreach(i => {
        logger.info(s"add $i")
        Queue2.add(SomeObject(i, s"text $i"))
        Thread.sleep(1000)
      })
    }

    val f2 = Future {
      val random = Random
      while (atomicInteger.get() < totalNumber) {
        val so = Queue2.get()
        if (so.isDefined) {
          logger.info(s"f2: ${so.get}")
          atomicInteger.incrementAndGet()
        } else {
          Thread.sleep(random.nextInt(500))
        }
      }
    }

    val f3 = Future {
      val random = Random
      while (atomicInteger.get() < totalNumber) {
        val so = Queue2.get()
        if (so.isDefined) {
          logger.info(s"f3: ${so.get}")
          atomicInteger.incrementAndGet()
        } else {
          Thread.sleep(random.nextInt(500))
        }
      }
    }

    Await.result(Future.sequence(List(f2, f3)), Duration.Inf)

    logger.info("<==test3")
  }
}
