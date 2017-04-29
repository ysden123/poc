package com.stulsoft.scalamock.examples.simple

import org.scalamock.scalatest.MockFactory
import org.scalatest.{FlatSpec, Matchers}

/**
  * Unit test.
  *
  * Usage of stub
  *
  * @author Yuriy Stul
  */
class TeamTest extends FlatSpec with MockFactory with Matchers {

  behavior of "Team"

  "teamSize" should "return size of team" in {
    // Usage of stub (Record-then-Verify)
    val fakedDbManager = stub[DbManager]
    (fakedDbManager.getTeam _).when("team1").returns(Some(Team("team1", Seq(Person("david", 10), Person("dan", 11)))))
    val person = fakedDbManager.getTeam("team1").get
    person.teamSize should equal(2)
  }
}
