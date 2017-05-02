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

import com.mongodb.MongoCredential
import org.mongodb.scala.bson.collection.mutable.Document
import org.mongodb.scala.{Completed, FindObservable, MongoClient, MongoClientSettings, MongoCollection, MongoDatabase, Observable,Observer, ReadPreference, ServerAddress}
import org.mongodb.scala.connection.ClusterSettings
import com.mongodb.MongoCredential._
import java.util.logging.{Level, Logger}
import org.mongodb.scala.connection.{NettyStreamFactoryFactory,SslSettings}
import scala.collection.JavaConverters._


/**
  * Usage of case classes with MongoDB Scala driver on Clod
  * @author Yuriy Stul
  */
object Cloud1 extends App with LazyLogging {

  private lazy val codecRegistry = fromRegistries(fromProviders(classOf[Person]), DEFAULT_CODEC_REGISTRY)


  val clusterSettings: ClusterSettings = ClusterSettings.builder().hosts(List(new ServerAddress("cluster0-shard-00-00-k8h55.mongodb.net:27017"),
    new ServerAddress("cluster0-shard-00-01-k8h55.mongodb.net:27017"),
    new ServerAddress("cluster0-shard-00-02-k8h55.mongodb.net:27017")
  ).asJava).build()
  val user: String = "<USER NAME>>"
  val databasename: String = "testDb"
  val password: Array[Char] = "<PASSWORD>".toCharArray
  val credential: MongoCredential = createCredential(user, databasename, password)
  val settings: MongoClientSettings = MongoClientSettings.builder()
    .clusterSettings(clusterSettings).credentialList(List(credential,credential).asJava).sslSettings(SslSettings.builder().enabled(true).build())
    .streamFactoryFactory(NettyStreamFactoryFactory()).build()
  val client: MongoClient = MongoClient(settings)

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

