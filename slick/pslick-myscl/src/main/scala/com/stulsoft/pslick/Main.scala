package com.stulsoft.pslick

import org.slf4j.LoggerFactory
import slick.jdbc.MySQLProfile.api._

/**
  * @author Yuriy Stul
  */
object Main extends App {
  val logger = LoggerFactory.getLogger(getClass)

  val db = Database.forConfig("db")
  val playingWithSuppliers = new PlayingWithSuppliers(db)

  try {
    logger.info("Started")
    Queries.readAllCoffees(db)
    Queries.conversions(db)
    Queries.join1(db)
    Queries.join2(db)
    Queries.printResult(db)
    Queries.coffeeById(db)
    Queries.coffeeByName(db)

    PlainQueries.printAllCoffees(db)
    PlainQueries.printAllCoffees2(db)
    PlainQueries.printAllCoffees3(db)

    playingWithSuppliers.printAllSuppliers

    val playingWithCoffees = new PlayingWithCoffees(db)
    playingWithCoffees.printAllCoffeesWithSuplliers()
  }
  catch {
    case e: Exception => logger.error(e.getMessage, e)
  }
  finally {
    db.close
    logger.info("Finished")
  }


}
