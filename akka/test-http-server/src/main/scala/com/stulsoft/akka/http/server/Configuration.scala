/*
 * Copyright (c) 2019. Yuriy Stul
 */

package com.stulsoft.akka.http.server

import com.typesafe.config.ConfigFactory

import scala.collection.JavaConverters._

/**
  * @author Yuriy Stul
  */
case class Configuration(fileName: String) {
  private val config = ConfigFactory.load(fileName)
  val validateXmlRequest:Boolean = config.getBoolean("validateXmlRequest")
  val xmlFile: String = config.getString("xmlFile")
  val statusCode: Int = config.getInt("statusCode")
  val headers: Seq[Header] = config
    .getConfigList("headers")
    .asScala
    .map(conf => Header(conf.getString("name"), conf.getString("value")))

  override def toString: String = "Configuration:" +
    s" fileName: $fileName" +
    s", xmlFile: $xmlFile" +
    s", statusCode: $statusCode" +
    s", headers: $headers"
}
