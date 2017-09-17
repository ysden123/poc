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
  private var dataFrameValues: DataFrame = _
  private var dictionaryValues: Set[(String, Int)] = _

  /**
    * Returns a data frame
    *
    * @return the data frame
    */
  def dataFrame: DataFrame = dataFrameValues

  /**
    * Returns a dictionary
    *
    * @return the dictionary (word, index)
    */
  def wordDictionary: Set[(String, Int)] = dictionaryValues

  /**
    * Returns word for specified index
    *
    * @param index the index (1, ...)
    * @return the word for specified index
    */
  def getWordByIndex(index: Int): Option[String] = {
    dictionaryValues.find(e => e._2 == index) match {
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
    dictionaryValues.find(e => e._1 == word.toLowerCase) match {
      case Some(e) => Some(e._2)
      case None => None
    }
  }

  private def init(): Unit = {
    dictionaryValues = buildDictionary()
    dataFrameValues = buildDataFrame()
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
          dictionaryValues.find(e => e._1 == word) match {
            case Some((_, index)) => index
            case None => 0
          }
        })
        .sorted
        .map(index => s"$index:1")
        .mkString(" ")
    })
      .map(s => s"1 $s")
    val tempFile = File.createTempFile("dataFrame", ".txt")
    val pw = new PrintWriter(tempFile)
    rows.foreach(pw.println)
    pw.close()

    val data = spark.read.format("libsvm").load(tempFile.getAbsolutePath)
    data
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
    data.dataFrame.show()

    println("Dictionary:")
    println(data.wordDictionary)

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