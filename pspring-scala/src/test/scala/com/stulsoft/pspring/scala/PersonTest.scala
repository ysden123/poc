/*
   Created by Yuriy Stul 2018
*/
package com.stulsoft.pspring.scala

import org.scalatest.{FlatSpec, Matchers}

class PersonTest extends FlatSpec with Matchers {

  behavior of "PersonTest"

  it should "support method name" in {
    Person("test", 21).name shouldBe "test"
  }

  it should "support method age" in {
    Person("test", 21).age shouldBe 21
  }
}
