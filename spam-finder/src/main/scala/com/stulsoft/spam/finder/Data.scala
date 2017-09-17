/*
 * Copyright (c) 2017. Yuriy Stul
 */

package com.stulsoft.spam.finder

import java.io.{File, PrintWriter}

import org.apache.spark.sql.{DataFrame, SparkSession}

import scala.io.Source

/** Prepare data
  *
  * @author Yuriy Stul
  */
sealed case class Data(spark: SparkSession, path: String) {
  private var dataFrame: DataFrame = _
  private var dictionary: Set[(String, Int)] = _

  /**
    * Returna a data frame
    *
    * @return the data frame
    */
  def getDataFrame(): DataFrame = dataFrame

  /**
    * Returns a dictionary
    *
    * @return the dictionary (word, index)
    */
  def getDictionary(): Set[(String, Int)] = dictionary

  /**
    * Returns word for specified index
    *
    * @param index the index (1, ...)
    * @return the word for specified index
    */
  def getWordByIndex(index: Int): Option[String] = {
    dictionary.find(e => e._2 == index) match {
      case Some(e) => Some(e._1)
      case None => None
    }
  }

  /**
    * Returns index for specified word
    *
    * @param word the word
    * @return the index (1, ...) for specified word
    */
  def getIndexByWord(word: String): Option[Int] = {
    dictionary.find(e => e._1 == word.toLowerCase) match {
      case Some(e) => Some(e._2)
      case None => None
    }
  }

  private def init(): Unit = {
    dictionary = buildDictionary()
    dataFrame = buildDataFrame()
  }

  private def buildDictionary(): Set[(String, Int)] = {
    val src = Source.fromFile(Utils.getResourceFilePath(path))
    val lines = src.getLines().toList
    src.close()

    lines.flatMap(line => line.split("\\s+")
      .map(_.toLowerCase))
      .map(_.replaceAll(",", ""))
      .toSet[String].zipWithIndex
      .map(e => (e._1, e._2 + 1))
  }

  private def buildDataFrame(): DataFrame = {
    val src = Source.fromFile(Utils.getResourceFilePath(path))
    val lines = src.getLines().toList
    src.close()

    val rows = lines.map(line => {
      line.split("\\s+")
        .map(_.toLowerCase)
        .map(_.replaceAll(",", ""))
        .map(word => {
          dictionary.find(e => e._1 == word) match {
            case Some((_, index)) => index
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

  init()
}

object DataTest extends App {
  test()

  def test(): Unit = {
    println("==>test")
    val spark: SparkSession = SparkSession.builder.
      master("local")
      .appName("Test")
      .getOrCreate()

    val data = Data(spark, "training.txt")

    println("DataFrame:")
    data.getDataFrame().show()

    println("Dictionary:")
    println(data.getDictionary())

    data.getIndexByWord("cheap") match {
      case Some(index) => println(s"cheap has index $index")
      case None => println(s"cheap has not index")
    }

    data.getWordByIndex(7) match {
      case Some(word) => println(s"Index 7 refer to $word")
      case None => println(s"Index 7 doesn't refer to any word")
    }

    spark.stop()
    println("<==test")
  }
}