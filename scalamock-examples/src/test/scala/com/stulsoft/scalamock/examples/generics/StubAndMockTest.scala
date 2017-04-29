/*
 * Copyright (c) 2017. Yuriy Stul
 */

package com.stulsoft.scalamock.examples.generics

import org.scalamock.scalatest.MockFactory
import org.scalatest.{FlatSpec, Matchers}

/**
  * Unit test.
  *
  * Usage of stub and mock
  *
  * @author Yuriy Stul.
  */
class StubAndMockTest extends FlatSpec with MockFactory with Matchers {
  behavior of "Generic Stub"
  "stub"  should "work with generics class" in {
    class AClass[T](v: T) {
      def getVAsString: String = {
        v.toString
      }
    }
    class ATest(v:Int) extends AClass
    val fakedAClass = stub[ATest]
    (fakedAClass.getVAsString _).when().returning("123")
    val result = fakedAClass.getVAsString
    println(s"result: $result")
  }

  "mock" should "work with generics class" in {
    class AClass[T](v: T) {
      def getVAsString: String = {
        v.toString
      }
    }
    class ATest(v:Int) extends AClass
    val fakedAClass = mock[ATest]
    (fakedAClass.getVAsString _).expects().returning("123")
    val result = fakedAClass.getVAsString
    println(s"result: $result")
  }
}
