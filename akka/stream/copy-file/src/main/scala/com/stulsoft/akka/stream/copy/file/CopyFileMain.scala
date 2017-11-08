package com.stulsoft.akka.stream.copy.file

import java.nio.file.Paths

import com.typesafe.scalalogging.LazyLogging
import akka.actor.ActorSystem
import akka.stream.{ActorMaterializer, IOResult}
import akka.stream.scaladsl.{FileIO, RunnableGraph, Sink, Source}
import akka.util.ByteString

import scala.concurrent.{ExecutionContextExecutor, Future}
import java.nio.file.StandardOpenOption._

/**
  * @author Yuriy Stul.
  */
object CopyFileMain extends App with LazyLogging {
  test()

  def test(): Unit = {
    logger.info("test started")
    val inputFile = "copy-file-src-1.txt"
    val outputFile = "copy-file-src-1-result.txt"
    val source: Source[ByteString, Future[IOResult]] =
      FileIO.fromPath(Paths.get(inputFile))
    val sink: Sink[ByteString, Future[IOResult]] =
      FileIO.toPath(Paths.get(outputFile), Set(CREATE, WRITE, APPEND))
    val runnableGraph: RunnableGraph[Future[IOResult]] =
      source.to(sink)

    implicit val system: ActorSystem = ActorSystem()
    implicit val ec: ExecutionContextExecutor = system.dispatcher
    implicit val materializer: ActorMaterializer = ActorMaterializer()
    runnableGraph.run().foreach { result =>
      logger.info(s"${result.status}, ${result.count} bytes read.")
      system.terminate()
    }

    logger.info("test finished")
  }
}
