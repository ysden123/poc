package com.stulsoft.pscalatest.prod.integrationTest

import com.stulsoft.pscalatest.prod.Prod1

import org.scalatest.{FeatureSpec, GivenWhenThen}

/**
  * Created by Yuriy Stul on 9/24/2016.
  */
class Prod1FeatureTest extends FeatureSpec with GivenWhenThen {
  info("As owner")
  info("I want increase age")
  info("So I can increase age when I want")
  feature("Prod1") {
    scenario("Increasing p1") {
      Given("a Prod1 object with p1 = 1")
      val p = Prod1(1, "test")
      When("increase p1 by 1")
      p.incrementP1(1)
      Then("p1 must be 2")
      assert(p.p1 == 2)
    }
  }
}
