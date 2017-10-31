package com.stulsoft.scalamock.examples.expectationFirst

import org.scalamock.scalatest.MockFactory
import org.scalatest.{FlatSpec, Matchers}

/** Expectations-First Style
  *
  * @author Yuriy Stul.
  */
class Class101ConsumerTest extends FlatSpec with MockFactory with Matchers {
  behavior of "Class101Consumer"
  "nameLengthCalculator" should "return length of a name" in {
    val class101Mock = mock[Class101]
    (class101Mock.nameLength _).expects().returning(3)
    val class101Consumer = Class101Consumer(class101Mock)

    class101Consumer.nameLengthCalculator()
  }
}
