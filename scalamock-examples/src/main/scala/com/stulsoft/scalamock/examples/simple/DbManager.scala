package com.stulsoft.scalamock.examples.simple

/**
  * Database manager
  *
  * @author Yuriy Stul
  */
trait DbManager {
  def getTeam(name: String): Option[Team]
}
