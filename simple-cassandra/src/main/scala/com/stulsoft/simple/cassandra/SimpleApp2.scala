/*
 * Copyright (c) 2017. Yuriy Stul
 */

package com.stulsoft.simple.cassandra

import java.util.concurrent.TimeUnit

import com.datastax.driver.core.Cluster

import scala.concurrent.Await
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration

/**
  * @author Yuriy Stul
  */
object SimpleApp2 extends App {
  test()

  def test(): Unit = {
    println("==>test")
    val cluster = {
      Cluster.builder()
        .addContactPoint("localhost")
        // .withCredentials("username", "password")
        .build()
    }

    val session = cluster.connect("test1")
    val todoDao = TodoDAO(session)

    println("Create table")
    val f1 = todoDao.createTable
    Await.ready(f1, Duration(10, TimeUnit.SECONDS))

    println("Inserting items")
    (1 to 3).foreach(i => {
      val todo = TodoDTO(i, s"The item # $i")
      Await.ready(todoDao.insert(todo), Duration(10, TimeUnit.SECONDS))
    })

    println("Items:")
    Await.result(todoDao.select, Duration(10, TimeUnit.SECONDS))
      .foreach(println)

    println("Drop table")
    Await.ready(todoDao.dropTable, Duration(10, TimeUnit.SECONDS))

    session.close()
    cluster.close()
    println("Done!")

    println("<==test")
  }
}
