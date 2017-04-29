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
object Example1 extends App with LazyLogging {
  logger.info("start")
  try {
    // To directly connect to the default server localhost on port 27017
    val dbClient: MongoClient = MongoClient()
    val database = dbClient.getDatabase("testDb")
    val collection = database.getCollection("testCollection")
    val testDoc = Document("name" -> "row1")

    val results = collection.insertOne(testDoc).results()

    logger.debug(s"results: $results")

    val count = collection.count().results().head
    logger.debug(s"count is $count")
    collection.drop().headResult()
  }
  catch {
    case e: Exception => println(s"Error: ${e.getMessage}")
  }
  logger.info("finish")
}
