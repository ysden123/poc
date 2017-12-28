package com.stulsoft.avro

import java.io.ByteArrayOutputStream

import org.apache.avro.Schema
import org.apache.avro.generic.{GenericData, GenericRecord}
import org.apache.avro.io.{DecoderFactory, EncoderFactory}
import org.apache.avro.specific.{SpecificDatumReader, SpecificDatumWriter}

import scala.io.Source

/** Playing with Avro
  *
  * @author Yuriy Stul.
  * @see [[https://dzone.com/articles/kafka-avro-scala-example Kafka Avro Scala Example]]
  */
object Main1 extends App {
  test1()
  test2()

  /**
    * Binary encoding
    */
  def test1(): Unit = {
    println("==>test1")
    val schema = new Schema.Parser().parse(Source.fromURL(getClass.getResource("/user.json")).mkString)
    val genericUser = new GenericData.Record(schema)

    genericUser.put("name", "test 1")
    genericUser.put("favoriteNumber", 1)
    genericUser.put("favoriteColor", "red")
    println(genericUser)

    val writer = new SpecificDatumWriter[GenericRecord](schema)
    val out = new ByteArrayOutputStream()
    val encoder = EncoderFactory.get().binaryEncoder(out, null)
    writer.write(genericUser, encoder)
    encoder.flush()
    out.close()

    val serializedBytes = out.toByteArray
    println(serializedBytes)
    println(s"serializedBytes.length=${serializedBytes.length}")

    val reader = new SpecificDatumReader[GenericRecord](schema)
    val decoder = DecoderFactory.get().binaryDecoder(serializedBytes, null)
    val userData = reader.read(null, decoder)
    //    println(userData)
    println(s"""${userData.get("name")} ${userData.get("favoriteNumber")} ${userData.get("favoriteColor")}""")

    println("<==test1")
  }

  /**
    * Json encoding
    */
  def test2(): Unit = {
    println("==>test2")
    val schema = new Schema.Parser().parse(Source.fromURL(getClass.getResource("/user.json")).mkString)
    val genericUser = new GenericData.Record(schema)

    genericUser.put("name", "test 2")
    genericUser.put("favoriteNumber", 1)
    genericUser.put("favoriteColor", "red")
    println(genericUser)

    val writer = new SpecificDatumWriter[GenericRecord](schema)
    val out = new ByteArrayOutputStream()
    val encoder = EncoderFactory.get().jsonEncoder(schema, out)
    writer.write(genericUser, encoder)
    encoder.flush()
    out.close()

    val serializedString = out.toString
    println(serializedString)
    println(s"serializedString.length=${serializedString.length}")

    val reader = new SpecificDatumReader[GenericRecord](schema)
    val decoder = DecoderFactory.get().jsonDecoder(schema, serializedString)
    val userData = reader.read(null, decoder)
    //    println(userData)
    println(s"""${userData.get("name")} ${userData.get("favoriteNumber")} ${userData.get("favoriteColor")}""")

    println("<==test2")
  }
}
