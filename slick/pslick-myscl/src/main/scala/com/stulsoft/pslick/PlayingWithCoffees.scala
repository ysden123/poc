package com.stulsoft.pslick

import slick.jdbc.MySQLProfile.api._
import slick.lifted.TableQuery

import scala.concurrent.Await
import scala.concurrent.duration._

/**
  * @author Yuriy Stul
  * @since 4/23/2018
  */
class PlayingWithCoffees(val db: Database) {
  import scala.concurrent.ExecutionContext.Implicits.global
  private val coffees = TableQuery[Coffees]
  private val suppliers = TableQuery[Suppliers]

  def printAllCoffeesWithSuplliers(): Unit = {
    println("==>printAllCoffeesWithSuplliers")
    val query = for {
      c <- coffees
      s <- suppliers if s.id === c.supID
    } yield (c, s)
    val run = db.run(query.result)
        .map(_.foreach{r=>println(r._1.toString ++ " " ++ r._2.toString)})
    println("<==printAllCoffeesWithSuplliers")
  }

}
