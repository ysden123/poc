package com.stulsoft.scalamock.examples.simple

/**
  * Team
  *
  * @author Yuriy Stul
  */
case class Team(name: String, staff: Seq[Person]) {
  def teamSize: Int = staff.size
}
