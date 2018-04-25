/*
 * Copyright (c) 2018. Yuriy Stul
 */

package com.stulsoft.pslick

import slick.jdbc.GetResult
import slick.jdbc.H2Profile.api._

import scala.concurrent.Await
import scala.concurrent.duration.Duration

/**
  * @author Yuriy Stul
  */
object PlainQueries {

  import scala.concurrent.ExecutionContext.Implicits.global

  def printAllCoffees(db: Database): Unit = {
    println("==>printAllCoffees")
    Await.result(
      db.run(sql"""select * from coffees""".as[(Int, String, Double, Int, Int)]).map(_.foreach(r => println(s"${r._1}, ${r._2}, ${r._3}, ${r._4}, ${r._5}"))),
      Duration.Inf)
    println("<==printAllCoffees")
  }

  def printAllCoffees2(db: Database): Unit = {
    println("==>printAllCoffees2")
    Await.result(
      db.run(sql"""select * from coffees""".as[(Int, String, Double, Int, Int)]).map(_.map(r => Coffee(r._1, r._2, r._3, r._4, r._5))
        .foreach(println)),
      Duration.Inf
    )
    println("<==printAllCoffees2")
  }

  def printAllCoffees3(db: Database): Unit = {
    implicit val getCoffeeResult = GetResult(r => Coffee(r.<<, r.<<, r.<<, r.<<, r.<<))
    println("==>printAllCoffee3")
    Await.result(
      db.run(sql"""select * from coffees""".as[Coffee]).map(_.foreach(c => println(c))),
      Duration.Inf
    )
    println("<==printAllCoffee3")
  }
}
