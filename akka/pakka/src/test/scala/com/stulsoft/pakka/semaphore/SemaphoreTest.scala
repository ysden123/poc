package com.stulsoft.pakka.semaphore

import java.util.concurrent.TimeoutException

import com.typesafe.scalalogging.LazyLogging
import org.scalatest.{FlatSpec, Matchers}

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._
import scala.util.{Failure, Success}

/**
  * Created by Yuriy Stul on 10/12/2016.
  */
class SemaphoreTest extends FlatSpec with Matchers with LazyLogging {
  behavior of "Semaphore"
  "apply" should "create new instance" in {
    val s = Semaphore(5, 10.seconds)
    s shouldBe a[Semaphore]
  }
  it should "prevent zero capacity" in {
    an[IllegalArgumentException] should be thrownBy Semaphore(0, 10.seconds)
  }
  it should "allow to use few semaphores" in {
    val s1 = Semaphore(1, 10.seconds)
    val s2 = Semaphore(1, 10.seconds)
    val s3 = Semaphore(1, 10.seconds)
    val s4 = Semaphore(1, 10.seconds)

    s1 shouldBe a[Semaphore]
    s2 shouldBe a[Semaphore]
    s3 shouldBe a[Semaphore]
    s4 shouldBe a[Semaphore]

    s1.acquire() match {
      case Success(v) => info("Working")
      case Failure(e) => fail(e)
    }

    s2.acquire() match {
      case Success(v) => info("Working")
      case Failure(e) => fail(e)
    }

    s3.acquire() match {
      case Success(v) => info("Working")
      case Failure(e) => fail(e)
    }

    s4.acquire() match {
      case Success(v) => info("Working")
      case Failure(e) => fail(e)
    }
  }

  "acquire" should "return immediately" in {
    val s = Semaphore(5, 10.seconds)
    s.acquire() match {
      case Success(v) => info("Working")
      case Failure(e) => fail(e)
    }
    info("Working")
    Thread.sleep(500)
  }

  it should "return with delay" in {
    var numberOfResources = 0
    val s = Semaphore(1, 10.seconds)
    s.acquire() match {
      case Success(v) =>
        numberOfResources += 1
        logger.info("Got 1st")
        logger.info("Start thread to release resource")
        Future {
          Thread.sleep(2000)
          logger.info("call release")
          s.release()
        }
      case Failure(e) => fail(e)
    }
    logger.info("Ask next")
    // Release previous
    s.acquire() match {
      case Success(v) =>
        numberOfResources += 1
        logger.info("Got 2nd")
      case Failure(e) => fail(e)
    }
    numberOfResources should equal(2)
  }

  it should "fail with timeout" in {
    val s = Semaphore(1, 1.seconds)
    s.acquire()
    s.acquire() match {
      case Success(v) => fail("missing timeout exception")
      case Failure(e) =>
        info("Happened TimeoutException")
        e shouldBe a [TimeoutException]
    }
  }

  "release" should "return immediately" in {
    val s = Semaphore(1, 10.seconds)
    s.release()
    Thread.sleep(1000)
  }
}
