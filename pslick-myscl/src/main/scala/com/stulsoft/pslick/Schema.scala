package com.stulsoft.pslick

import slick.jdbc.MySQLProfile.api._
import slick.lifted.{ForeignKeyQuery, ProvenShape, Rep, Tag}

import scala.concurrent.Future

case class Coffee(name: String, price: Double, sales: Int, total: Int, supID: Long = 0)

/**
  * Definition of the COFFEES table
  *
  * @author Yuriy Stul
  */
class Coffees(tag: Tag) extends Table[Coffee](tag, "COFFEES") {
  def * : ProvenShape[Coffee] = (name, price, sales, total, supID) <> (Coffee.tupled, Coffee.unapply)

  def name: Rep[String] = column[String]("COF_NAME", O.PrimaryKey)

  def price: Rep[Double] = column[Double]("PRICE")

  def sales: Rep[Int] = column[Int]("SALES")

  def total: Rep[Int] = column[Int]("TOTAL")

  // A reified foreign key relation that can be navigated to create a join
  def supplier: ForeignKeyQuery[Suppliers, Supplier] = foreignKey("SUP_FK", supID, suppliers)(_.id)

  def supID: Rep[Long] = column[Long]("SUP_ID")
}

object CoffeesDAO extends TableQuery(new Coffees(_)) {

  import scala.concurrent.ExecutionContext.Implicits.global

  /**
    * Returns a Coffee for specified ID
    *
    * @param db the database
    * @param id the id
    * @return the Coffee for specified ID
    */
  def findById(db: Database, id: Long): Future[Option[Coffee]] = {
    db.run(this.filter(_.supID === id).result).map(_.headOption)
  }

  /**
    * Returns a Coffee for specified name
    *
    * @param db   the database
    * @param name the name
    * @return the Coffee for specified name
    */
  def findByName(db: Database, name: String): Future[Option[Coffee]] = {
    db.run(this.filter(_.name === name).result).map(_.headOption)
  }
}

case class Supplier(id: Long = 0, name: String, street: String, city: String, state: String, zip: String)

/**
  * Definition of the SUPPLIERS table
  *
  * @author Yuriy Stul
  */
class Suppliers(tag: Tag) extends Table[Supplier](tag, "SUPPLIERS") {

  // Every table needs a * projection with the same type as the table's type parameter
  def * : ProvenShape[Supplier] = (id, name, street, city, state, zip) <> (Supplier.tupled, Supplier.unapply)

  def id: Rep[Long] = column[Long]("SUP_ID", O.PrimaryKey, O.AutoInc)

  // This is the primary key column
  def name: Rep[String] = column[String]("SUP_NAME")

  def street: Rep[String] = column[String]("STREET")

  def city: Rep[String] = column[String]("CITY")

  def state: Rep[String] = column[String]("STATE")

  def zip: Rep[String] = column[String]("ZIP")

}