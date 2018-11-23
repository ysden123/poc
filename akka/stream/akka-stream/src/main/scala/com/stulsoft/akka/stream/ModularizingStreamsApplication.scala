/*
 * Copyright (c) 2018. Yuriy Stul
 */

package com.stulsoft.akka.stream

import java.nio.file.Paths

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.stream.scaladsl._
import akka.util.ByteString
import com.typesafe.scalalogging.LazyLogging

/**
  * @author Yuriy Stul
  */
object ModularizingStreamsApplication extends App with LazyLogging {
  implicit val actorSystem: ActorSystem =
    ActorSystem("TransformingStream")
  implicit val actorMaterializer: ActorMaterializer = ActorMaterializer()
  val MaxGroups = 1000
  val path = Paths.get("akka-stream/src/main/resources/gzipped-file.txt")
  val source = FileIO.fromPath(path)

  val utf8UppercaseMapper =
    Flow[ByteString].map(_.utf8String.toUpperCase)

  val utf8LowercaseMapper =
    Flow[ByteString].map(_.utf8String.toLowerCase)

  val splitter = Flow[String].mapConcat(_.split(" ").toList)

  val punctuationMapper = Flow[String].map(_.replaceAll("""[p{Punct}&&[^.]]""".stripMargin, "")
    .replaceAll(System.lineSeparator(), ""))

  val filterEmptyElements = Flow[String].filter(_.nonEmpty)

  val wordCountFlow = Flow[String]
    .groupBy(MaxGroups, identity)
    .map(_ -> 1)
    .reduce((l, r) => (l._1, l._2 + r._2))
    .mergeSubstreams

  val printlnSink = Sink.foreach(println)

  val streamUppercase = source
    .via(utf8UppercaseMapper)
    .via(splitter)
    .via(punctuationMapper)
    .via(filterEmptyElements)
    .via(wordCountFlow)
    .to(printlnSink)

  val streamLowercase = source
    .via(utf8LowercaseMapper)
    .via(splitter)
    .via(punctuationMapper)
    .via(filterEmptyElements)
    .via(wordCountFlow)
    .to(printlnSink)

  streamUppercase.run()

  streamLowercase.run()

  Thread.sleep(500)
  actorSystem.terminate()
}
