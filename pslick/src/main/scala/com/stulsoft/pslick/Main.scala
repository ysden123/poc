package com.stulsoft.pslick

import org.slf4j.LoggerFactory
import slick.jdbc.H2Profile.api._

/**
  * @author Yuriy Stul
  */
object Main extends App {
val logger = LoggerFactory.getLogger(getClass)

  val db = Database.forConfig("h2mem1")

  try {
    logger.info("Started")
    Queries.init(db)
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
  }
  finally {
    db.close
    logger.info("Finished")
  }


}
