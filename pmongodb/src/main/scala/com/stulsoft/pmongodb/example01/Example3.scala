/*
 * Copyright (c) 2017. Yuriy Stul
 */

package com.stulsoft.pmongodb.example01

import com.stulsoft.pmongodb.example01.Helpers._
import com.typesafe.scalalogging.LazyLogging
import org.bson.codecs.configuration.CodecRegistries.{fromProviders, fromRegistries}
import org.mongodb.scala.bson.ObjectId
import org.mongodb.scala.bson.codecs.DEFAULT_CODEC_REGISTRY
import org.mongodb.scala.bson.codecs.Macros._
import org.mongodb.scala.model.Filters._
import org.mongodb.scala.{MongoClient, MongoCollection, MongoDatabase}

/**
  * Usage of case classes with MongoDB Scala driver
  *
  * @see [[http://mongodb.github.io/mongo-scala-driver/2.0/getting-started/quick-tour-case-classes/]]
  * @author Yuriy Stul
  */
object Example3 extends App with LazyLogging {

  private lazy val codecRegistry = fromRegistries(fromProviders(classOf[Person]), DEFAULT_CODEC_REGISTRY)

  // To directly connect to the default server localhost on port 27017
  val client: MongoClient = MongoClient()
  val database: MongoDatabase = client.getDatabase("testDb").withCodecRegistry(codecRegistry)
  val collection: MongoCollection[Person] = database.getCollection("testCollection3")
  collection.drop().results()

  val person: Person = Person("Ada", "Lovelace")
  collection.insertOne(person).results()

  collection.find().first().printHeadResult()
  collection.find().printResults()
  collection.find(equal("firstName", "Ada")).first().printHeadResult()
  collection.find(equal("lastName", "Lovelace")).first().printHeadResult()

  case class Person(_id: ObjectId, firstName: String, lastName: String)

  object Person {
    def apply(firstName: String, lastName: String): Person =
      Person(new ObjectId(), firstName, lastName)
  }

}
