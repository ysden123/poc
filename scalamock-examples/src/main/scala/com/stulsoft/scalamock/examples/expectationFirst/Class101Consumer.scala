package com.stulsoft.scalamock.examples.expectationFirst

/**
  * @author Yuriy Stul.
  */
case class Class101Consumer(class101: Class101) {
  def nameLengthCalculator(): Int = class101.nameLength()
//  def nameLengthCalculator(): Int = class101.nameLength() + class101.nameLength() // ===> Error in Unit Test - calls class101.nameLength() more than once
//  def nameLengthCalculator(): Int = 3 // ==> Error in Unit Test - doesn't call class101.nameLength()
}
