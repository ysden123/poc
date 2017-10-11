/*
 * Copyright (c) 2017. Yuriy Stul
 */

package com.stulsoft.aws.test1

/**
  * @see [[https://aws.amazon.com/ru/blogs/compute/writing-aws-lambda-functions-in-scala/]]
  * @author Yuriy Stul
  */

import java.net.URLDecoder

import com.amazonaws.services.lambda.runtime.events.S3Event

import scala.collection.JavaConverters._

class Main {
  def getSourceBuckets(event: S3Event): java.util.List[String] = {
    val result = event.getRecords.asScala.map(record => decodeS3Key(record.getS3.getObject.getKey)).asJava
    println(result)
    result
  }

  def decodeS3Key(key: String): String = URLDecoder.decode(key.replace("+", " "), "utf-8")
}
