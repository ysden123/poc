package com.stulsoft.pslick

import slick.jdbc.MySQLProfile.api._

import scala.concurrent.Await
import scala.concurrent.duration._


/**
  * @author Yuriy Stul
  */
object Queries {

  import scala.concurrent.ExecutionContext.Implicits.global

  /**
    * Creates the tables, including primary and foreign keys.
    *
    * @param db the database
    */
//  def init(db: Database): Unit = {
//    println("==>init")
//    val setup = DBIO.seq(
//      (suppliers.schema ++ coffees.schema).create
//
//      // Insert some suppliers
////      suppliers += Supplier(101L, "Acme, Inc.", "99 Market Street", "Groundsville", "CA", "95199"),
////      suppliers += Supplier(49L, "Superior Coffee", "1 Party Place", "Mendocino", "CA", "95460"),
////      suppliers += Supplier(150L, "The High Ground", "100 Coffee Lane", "Meadows", "CA", "93966"),
//      // Equivalent SQL code:
//      // insert into SUPPLIERS(SUP_ID, SUP_NAME, STREET, CITY, STATE, ZIP) values (?,?,?,?,?,?)
//
//      // Insert some coffees (using JDBC's batch insert feature, if supported by the DB)
////      coffees ++= Seq(
////        Coffee(101, "Colombian", 7.99, 0, 0),
////        Coffee(49, "French_Roast", 8.99, 0, 0),
////        Coffee(150, "Espresso", 9.99, 0, 0),
////        Coffee(101, "Colombian_Decaf", 8.99, 0, 0),
////        Coffee(49, "French_Roast_Decaf", 9.99, 0, 0)
////      )
//      // Equivalent SQL code:
//      // insert into COFFEES(COF_NAME, SUP_ID, PRICE, SALES, TOTAL) values (?,?,?,?,?)
//    )
//
//    val setupFuture = db.run(setup)
//
//    val result1 = Await.result(setupFuture, 2.seconds)
//    println(s"result1: $result1")
//  }

  /**
    * Read all coffees and print them to the console
    *
    * @param db the database
    */
  def readAllCoffees(db: Database): Unit = {
    println("==>readAllCoffees")
    println("Coffees:")
    val count: Int = Await.result(db.run(coffees.length.result), 2.seconds)
    println(s"count = $count")
    val run2 = db.run(coffees.result).map(_.foreach {
      case c: Coffee =>
        println("  " + c.name + "\t" + c.supID + "\t" + c.price + "\t" + c.sales + "\t" + c.total)
      case _ =>
        println("1")
    })
    // Equivalent SQL code:
    // select COF_NAME, SUP_ID, PRICE, SALES, TOTAL from COFFEES
    val result2 = Await.result(run2, 2.seconds)
    println(s"result2: $result2")
  }

  /**
    * Why not let the database do the string conversion and concatenation?
    *
    * @param db the database
    */
  def conversions(db: Database): Unit = {
    println("==>conversions")
    val q1 = for (c <- coffees)
      yield LiteralColumn("  ") ++ c.name ++ "\t" ++ c.supID.asColumnOf[String] ++
        "\t" ++ c.price.asColumnOf[String] ++ "\t" ++ c.sales.asColumnOf[String] ++
        "\t" ++ c.total.asColumnOf[String]
    // The first string constant needs to be lifted manually to a LiteralColumn
    // so that the proper ++ operator is found

    // Equivalent SQL code:
    // select '  ' || COF_NAME || '\t' || SUP_ID || '\t' || PRICE || '\t' SALES || '\t' TOTAL from COFFEES

    val r = db.stream(q1.result).foreach(println)
    val result = Await.result(r, 2.seconds)
    println(s"(conversions) result: $result")
  }

  def join1(db: Database): Unit = {
    println("==>join1")

    // Perform a join to retrieve coffee names and supplier names for
    // all coffees costing less than $9.00
    val q2 = for {
      c <- coffees if c.price < 9.0
      s <- suppliers if s.id === c.supID
    } yield (c.name, s.name)
    // Equivalent SQL code:
    // select c.COF_NAME, s.SUP_NAME from COFFEES c, SUPPLIERS s where c.PRICE < 9.0 and s.SUP_ID = c.SUP_ID
    val r = db.stream(q2.result).foreach(println)
    val result = Await.result(r, 2.seconds)
    println(s"(join) result: $result")
  }

  def join2(db: Database): Unit = {
    println("==>join2")

    val q3 = for {
      c <- coffees if c.price < 9.0
      s <- c.supplier
    } yield (c.name, s.name)
    // Equivalent SQL code:
    // select c.COF_NAME, s.SUP_NAME from COFFEES c, SUPPLIERS s where c.PRICE < 9.0 and s.SUP_ID = c.SUP_ID

    val r = db.stream(q3.result).foreach(println)
    val result = Await.result(r, 2.seconds)
    println(s"(join) result: $result")
  }

  def printResult(db: Database): Unit = {
    println("==>getResult")
    val q = for {
      c <- coffees if c.price < 9.0
    } yield c

    val f = db.run(q.result)
    val result = Await.result(f, 2.seconds)
    println(s"result: $result")
  }

  /*def getResult1(db: Database): List[Coffees] = {
    println("==>getResult1")
    val q = for {
      c <- coffees if c.price < 9.0
    } yield c

    val f = db.run(q.result)
    val result = Await.result(f, 2.seconds)
    result.toList
  }*/

  def coffeeById(db: Database): Unit = {
    println("==>coffeeById")
    var result = Await.result(CoffeesDAO.findById(db, 1), 2.seconds)
    println(s"(1) result.isDefined is ${result.isDefined}, result.isEmpty is ${result.isEmpty}")
    result.foreach(c => println(s"(1) result: $c"))

    result = Await.result(CoffeesDAO.findById(db, 101), 2.seconds)
    println(s"(101) result.isDefined is ${result.isDefined}, result.isEmpty is ${result.isEmpty}")
    result.foreach(c => println(s"(101) result: $c"))
  }

  def coffeeByName(db: Database): Unit = {
    println("==>coffeeByName")
    val result = Await.result(CoffeesDAO.findByName(db, "French_Roast"), 2.seconds)
    println(s"result.isDefined is ${result.isDefined}, result.isEmpty is ${result.isEmpty}")
    result.foreach(c => println(s"result: $c"))
  }
}
