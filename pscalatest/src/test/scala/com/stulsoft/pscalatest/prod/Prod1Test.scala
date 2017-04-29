package com.stulsoft.pscalatest.prod

import org.scalatest.{BeforeAndAfterEach, FlatSpec, Matchers}

/**
  * Unit test example.
  *
  * Created by Yuriy Stul on 9/24/2016.
  */
class Prod1Test extends FlatSpec with BeforeAndAfterEach with Matchers {

  override def beforeEach() {
    println("==>beforeEach")
  }

  override def afterEach() {
    println("==>afterEach")
  }

  behavior of "Prod1Test (unit tests)"
  "toString" should "produce text representation of the instance" in {
    val p = Prod1(123, "just test")
    assert(p.toString == s"p1 = ${p.p1}, p2: ${p.p2}")
  }

  it should "not produce empty text" in {
    val p = Prod1(123, "just test")
    assert(p.toString.length > 0)
  }

  "incrementP1" should "increment parameter 1" in {
    val p = Prod1(123, "just test")
    p.incrementP1(100)
    assert(p.p1 == 223)
  }

  it should "not change p1, if increment is 0 " in{
    val p = Prod1(123, "just test")
    p.incrementP1(0)
    assert(p.p1 == 123)
  }
}
