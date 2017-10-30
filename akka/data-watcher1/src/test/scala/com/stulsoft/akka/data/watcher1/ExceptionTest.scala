/*
 * Copyright (c) 2017. Yuriy Stul
 */

package com.stulsoft.akka.data.watcher1

import com.stulsoft.akka.data.watcher1.Exceptions._
import org.scalatest.{FlatSpec, Matchers}

/**
  * @author Yuriy Stul
  */
class ExceptionTest extends FlatSpec with Matchers{
  behavior of "DiskErrorException"
  "throw DiskErrorException" should "produce exception with message without cause" in {
    val message = "the message"
    try {
      throw DiskErrorException(message)
    }
    catch {
      case DiskErrorException(theMessage, cause) =>
        theMessage shouldBe message
        cause shouldBe null
    }
  }

  it should "produce exception with message and cause" in {
    val message = "the message"
    val testMessage = "the test exception"
    val cause = new RuntimeException(testMessage)
    try {
      throw DiskErrorException(message, cause)
    }
    catch {
      case DiskErrorException(theMessage, theCause) =>
        theMessage shouldBe message
        theCause shouldBe cause
        theCause.getMessage shouldBe testMessage
    }
  }

  behavior of "FileCorruptException"
  "throw FileCorruptException" should "produce exception with message without cause" in {
    val message = "the message"
    try {
      throw FileCorruptException(message)
    }
    catch {
      case FileCorruptException(theMessage, cause) =>
        theMessage shouldBe message
        cause shouldBe null
    }
  }

  it should "produce exception with message and cause" in {
    val message = "the message"
    val testMessage = "the test exception"
    val cause = new RuntimeException(testMessage)
    try {
      throw FileCorruptException(message, cause)
    }
    catch {
      case FileCorruptException(theMessage, theCause) =>
        theMessage shouldBe message
        theCause shouldBe cause
        theCause.getMessage shouldBe testMessage
    }
  }

  behavior of "DBConnectionException"
  "throw DBConnectionException" should "produce exception with message without cause" in {
    val message = "the message"
    try {
      throw DBConnectionException(message)
    }
    catch {
      case DBConnectionException(theMessage, cause) =>
        theMessage shouldBe message
        cause shouldBe null
    }
  }

  it should "produce exception with message and cause" in {
    val message = "the message"
    val testMessage = "the test exception"
    val cause = new RuntimeException(testMessage)
    try {
      throw DBConnectionException(message, cause)
    }
    catch {
      case DBConnectionException(theMessage, theCause) =>
        theMessage shouldBe message
        theCause shouldBe cause
        theCause.getMessage shouldBe testMessage
    }
  }
}
