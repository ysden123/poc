/*
 * Copyright (c) 2018. Yuriy Stul
 */

package com.stulsoft.pslick

import slick.jdbc.H2Profile.api._
import slick.lifted.TableQuery

import scala.concurrent.Await
import scala.concurrent.duration._

/**
  * @author Yuriy Stul
  */
class PlayingWithSuppliers(val db: Database) {
  import scala.concurrent.ExecutionContext.Implicits.global
  val suppliers = TableQuery[Suppliers]

  def printAllSuppliers(): Unit = {
    println("==>printAllSuppliers")
    println("All suppliers:")
    val result = db.run(suppliers.result).map(_.foreach({
      s: Supplier => println(s)
    }))

    Await.result(result, 2.seconds)
    println("<==printAllSuppliers")
  }
}
