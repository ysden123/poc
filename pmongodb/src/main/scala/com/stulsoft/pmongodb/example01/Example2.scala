/*
 * Copyright (c) 2017. Yuriy Stul
 */

package com.stulsoft.pmongodb.example01

import com.stulsoft.pmongodb.example01.Helpers._
import com.typesafe.scalalogging.LazyLogging
import org.mongodb.scala.MongoClient
import org.mongodb.scala.bson.collection.immutable.Document

/**
  * @author Yuriy Stul
  */
object Example2 extends App with LazyLogging {

  case class MyDocument(name: String, age: Int) {
    def toDocument: Document = {
      Document("name" -> name, "age" -> age)
    }
  }

  logger.info("start")
  try {
    val dbClient: MongoClient = MongoClient(connectionString)
    val database = dbClient.getDatabase("testDb")
    val collection = database.getCollection("testCollection2")
    val testDoc = MyDocument("the name", 21).toDocument

    val results = collection.insertOne(testDoc).results()

    logger.debug(s"results: $results")

    val count = collection.countDocuments().results().head
    logger.debug(s"count is $count")

    collection.find().printResults()
    collection.drop().headResult()
  }
  catch {
    case e: Exception => println(s"Error: ${e.getMessage}")
  }
  logger.info("finish")

}
