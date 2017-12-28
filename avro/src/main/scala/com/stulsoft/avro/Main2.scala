/*
 * Copyright (c) 2017. Yuriy Stul
 */

package com.stulsoft.avro

import java.io.File

import org.apache.avro.Schema
import org.apache.avro.file.{DataFileReader, DataFileWriter}
import org.apache.avro.generic.{GenericData, GenericRecord}
import org.apache.avro.specific.{SpecificDatumReader, SpecificDatumWriter}

import scala.io.Source

/** Serialization/deserialization in/from file
  *
  * @author Yuriy Stul
  * @see [[http://avro.apache.org/docs/current/gettingstartedjava.html Apache Avro Getting Started (Java)]]
  */
object Main2 extends App {
  test1()

  def test1(): Unit = {
    println("==>test1")
    val fileName = "users.avro"
    val schema = new Schema.Parser().parse(Source.fromURL(getClass.getResource("/user.json")).mkString)

    // Serialization
    val user1 = new GenericData.Record(schema)
    user1.put("name", "test 1")
    user1.put("favoriteNumber", 1)
    user1.put("favoriteColor", "red")

    val user2 = new GenericData.Record(schema)
    user2.put("name", "test 2")
    user2.put("favoriteNumber", 12)
    user2.put("favoriteColor", "green")

    val user3 = new GenericData.Record(schema)
    user3.put("name", "test 3")
    user3.put("favoriteNumber", 123)
    user3.put("favoriteColor", "black")

    val userWriter = new SpecificDatumWriter[GenericRecord](schema)
    val fileWriter = new DataFileWriter[GenericRecord](userWriter)
    fileWriter.create(schema, new File(fileName))
    fileWriter.append(user1)
    fileWriter.append(user2)
    fileWriter.append(user3)
    fileWriter.close()

    // Deserialization
    val userReader = new SpecificDatumReader[GenericRecord](schema)
    val fileReader = new DataFileReader[GenericRecord](new File(fileName), userReader)
    //    while (fileReader.hasNext) {
    //      val user = fileReader.next()
    //      println(s"""${user.get("name")}, ${user.get("favoriteNumber")}, ${user.get("favoriteColor")}""")
    //    }

    fileReader.forEach(user => println(s"""${user.get("name")}, ${user.get("favoriteNumber")}, ${user.get("favoriteColor")}"""))
    fileReader.close()

    println("<==test1")
  }
}
