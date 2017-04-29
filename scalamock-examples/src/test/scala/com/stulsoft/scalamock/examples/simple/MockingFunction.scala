package com.stulsoft.scalamock.examples.simple

import org.scalamock.scalatest.MockFactory
import org.scalatest.{FlatSpec, Matchers}

/**
  * Mocking function
  *
  * @author Yuriy Stul
  */
class MockingFunction extends FlatSpec with MockFactory with Matchers {

  // Usage of mocking function for testing function as argument
  "faked function" should "should return result" in {
    val fakedFunction = mockFunction[Int, String]
    fakedFunction expects 1 returning "One"
    fakedFunction expects 2 returning "Two"

    def testMethod(f: (Int) => String, arg: Int): String = {
      f(arg)
    }

    testMethod(fakedFunction, 1) should equal("One")
    testMethod(fakedFunction, 2) should equal("Two")
  }

  // Usage of mocking function for testing exception handling
  it should "throw exception" in {
    val fakedFunction = mockFunction[Int, String]

    fakedFunction expects 0 throws new IllegalArgumentException("Error text")
    fakedFunction expects 1 returning "One"

    def testMethod(f: (Int) => String, arg: Int): String = {
      f(arg)
    }

    assertThrows[IllegalArgumentException] {
      testMethod(fakedFunction, 0)
    }
    testMethod(fakedFunction, 1)
  }

  //  Usage of mocking function for testing internal logic
  it should "support once" in {
    val fakedFunction = mockFunction[Int, String]

    fakedFunction expects 1 returning "One" once

    def testMethod(f: (Int) => String, arg: Int): String = {
      f(arg)
    }

    testMethod(fakedFunction, 1)
  }

  //  Usage of mocking function for testing internal logic
  it should "support n times " in {
    val fakedFunction = mockFunction[Int, String]

    fakedFunction expects 1 returning "One" repeated 2 times

    def testMethod(f: (Int) => String, arg: Int): String = {
      f(arg)
      f(arg)
    }

    testMethod(fakedFunction, 1)
  }

  //  Usage of mocking function for testing internal logic
  it should "support n times (with exception) " in {
    val fakedFunction = mockFunction[Int, String]

    fakedFunction expects 1 returning "One" repeated 2 times

    def testMethod(f: (Int) => String, arg: Int): String = {
      f(arg)
      f(arg)
      f(arg) // Error
    }

    assertThrows[Exception] {
      testMethod(fakedFunction, 1)
    }
  }
}

