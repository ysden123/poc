/*
 * Copyright (c) 2017. Yuriy Stul
 */

package com.stulsoft.simple.cassandra

import java.util.concurrent.TimeUnit

import com.datastax.driver.core._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent._
import scala.util.{Failure, Success}

/**
  * @see [[http://eax.me/scala-cassandra/]]
  * @author Yuriy Stul
  */
object SimpleApp extends App {
  val cluster = {
    Cluster.builder()
      .addContactPoint("localhost")
      // .withCredentials("username", "password")
      .build()
  }

  try {
    val session = cluster.connect("test1")
    val todoDao = TodoDAO(session)

    lazy val f = {
      for {
        _ <- {
          println("Creating table")
          todoDao.createTable
        }
        _ = println("Inserting items")
        _ <- {
          fTraverse(1 to 3) { n =>
            val item = TodoDTO(n, s"Todo item $n")
            todoDao.insert(item)
          }
        }
        items <- todoDao.select
        _ = println(s"Items: $items")
        _ = println("Deleting item 2")
        _ <- todoDao.delete(2)
        newItems <- todoDao.select
        _ = println(s"New items: $newItems")
        _ <- todoDao.dropTable
      } yield {}
    }

    f onComplete {
      case Success(r) => println(s"Success $r")
      case Failure(e) => println(s"ERROR: $e")
    }

    def fTraverse[A, B](xs: Seq[A])(f: A => Future[B]): Future[Seq[B]] = {
      if (xs.isEmpty) Future successful Seq.empty[B]
      else f(xs.head) flatMap { fh => fTraverse(xs.tail)(f) map (r => fh +: r) }
    }

    Await.ready(f, scala.concurrent.duration.Duration(30, TimeUnit.SECONDS))
    session.close()
    cluster.close()
    println("Done!")
  }
  catch{
    case e:Exception =>
      println(s"Failure: ${e.getMessage}")
      cluster.close()
  }
}