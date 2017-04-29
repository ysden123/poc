package com.stulsoft.pslick

import slick.jdbc.H2Profile.api._

/**
  * @author Yuriy Stul
  */
object Main extends App {


  val db = Database.forConfig("h2mem1")

  try {
    Queries.init(db)
    Queries.readAllCoffees(db)
    Queries.conversions(db)
    Queries.join1(db)
    Queries.join2(db)
    Queries.printResult(db)
    Queries.coffeeById(db)
    Queries.coffeeByName(db)
  }
  finally db.close


}
