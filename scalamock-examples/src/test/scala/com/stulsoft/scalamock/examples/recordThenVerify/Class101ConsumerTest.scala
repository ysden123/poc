package com.stulsoft.scalamock.examples.recordThenVerify

import com.stulsoft.scalamock.examples.expectationFirst.{Class101, Class101Consumer}
import org.scalamock.scalatest.MockFactory
import org.scalatest.{FlatSpec, Matchers}

/** Record-then-Verify Style
  *
  * @author Yuriy Stul.
  */
class Class101ConsumerTest extends FlatSpec with MockFactory with Matchers {
  behavior of "Class101Consumer"

  "nameLengthCalculator" should "return length of a name" in {
    val class101Stub = stub[Class101]
    (class101Stub.nameLength _).when().returns(3)
    val class101Consumer = Class101Consumer(class101Stub)

    class101Consumer.nameLengthCalculator()

    (class101Stub.nameLength _).verify()
  }
}
