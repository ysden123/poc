/*
 * Copyright (c) 2017. Yuriy Stul
 */

package com.stulsoft.spam.finder

import java.io.{File, PrintWriter}

import org.apache.spark.ml.Transformer
import org.apache.spark.ml.classification.LogisticRegression
import org.apache.spark.sql.{DataFrame, SparkSession}

import scala.io.Source

/** Prepare data
  *
  * @author Yuriy Stul
  */
sealed case class Data(spark: SparkSession, spamPath: String, notSpamPath: String) {
  private var dataFrameValues: DataFrame = _
  private var dictionaryValues: Set[(String, Int)] = _
  private var model: Transformer = _

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

    val lr = new LogisticRegression()
      .setMaxIter(10)
      .setRegParam(0.3)
      .setElasticNetParam(0.8)
    // Fit the model
    model = lr.fit(dataFrameValues)

    // Print the coefficients and intercept for logistic regression
    //    println(s"Coefficients: ${model.coefficients} Intercept: ${model.intercept}")
  }

  private def buildDictionary(): Set[(String, Int)] = {
    val spamSrc = Source.fromFile(Utils.getResourceFilePath(spamPath))
    val spamLines = spamSrc.getLines().toList
    spamSrc.close()

    val notSpamSrc = Source.fromFile(Utils.getResourceFilePath(notSpamPath))
    val notSpamLines = notSpamSrc.getLines().toList
    notSpamSrc.close()

    val lines = spamLines ++ notSpamLines

    lines.flatMap(line => line.split("\\s+")
      .map(_.toLowerCase))
      .map(_.replaceAll(",", ""))
      .toSet[String].zipWithIndex
      .map(e => (e._1, e._2 + 1))
  }

  private def buildDataFrame(): DataFrame = {
    val spamSrc = Source.fromFile(Utils.getResourceFilePath(spamPath))
    val spamLines = spamSrc.getLines().toList
    spamSrc.close()

    val notSpamSrc = Source.fromFile(Utils.getResourceFilePath(notSpamPath))
    val notSpamLines = notSpamSrc.getLines().toList
    notSpamSrc.close()

    val rows = spamLines.map(line => {
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
      .map(s => s"1 $s") ++ notSpamLines.map(line => {
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
      .map(s => s"0 $s")
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

    val data = Data(spark, "spam.txt", "not-spam.txt")

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