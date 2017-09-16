/*
 * Copyright (c) 2017. Yuriy Stul
 */

package com.stulsoft.spam.finder

import java.io.{File, PrintWriter}

import org.apache.spark.sql.{DataFrame, SparkSession}

import scala.io.Source

/**
  * @author Yuriy Stul
  */
object LibSvmBuilder {
  def build(spark: SparkSession, path: String): DataFrame = {
    val dictionary = buildDictionary(path)

    val src = Source.fromFile(Utils.getResourceFilePath(path))
    val lines = src.getLines().toList
    src.close()

    val rows = lines.map(line => {
      line.split("\\s+")
        .map(_.toLowerCase)
        .map(_.replaceAll(",", ""))
        .map(word => {
          dictionary.find(e => e._1 == word) match {
            case Some((_, index)) => index + 1
            case None => 0
          }
        })
        .sorted
        .map(index => s"$index:1")
        .mkString(" ")
    })
      .map(s => s"1 $s")
    val pw = new PrintWriter(new File("temp.txt"))
    rows.foreach(pw.println)
    pw.close()

    spark.read.format("libsvm").load("temp.txt")
  }

  private def buildDictionary(path: String): Set[(String, Int)] = {
    val src = Source.fromFile(Utils.getResourceFilePath(path))
    val lines = src.getLines().toList
    src.close()

    val dictionary = lines.flatMap(line => line.split("\\s+")
      .map(_.toLowerCase))
      .map(_.replaceAll(",", ""))
      .toSet[String].zipWithIndex
    dictionary
  }
}

object LibSvmBuilderTest extends App {
  test()

  def test(): Unit = {
    println("==>test")

    val spark: SparkSession = SparkSession.builder.
      master("local")
      .appName("Test")
      .getOrCreate()

    val data = LibSvmBuilder.build(spark, "training.txt")
    println(data)

    data.show()

    spark.stop()
    println("<==test")
  }
}
