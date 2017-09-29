/*
 * Copyright (c) 2017. Yuriy Stul
 */

package com.stulsoft.simple.cassandra

import com.datastax.driver.core._
import com.google.common.util.concurrent._

import scala.concurrent._

/**
  * @see [[http://eax.me/scala-cassandra/]]
  * @author Yuriy Stul
  */
object Utils {
  implicit def futCSToScala(f: ResultSetFuture): Future[ResultSet] = {
    val promise = Promise[ResultSet]()

    val callback = new FutureCallback[ResultSet] {
      def onSuccess(result: ResultSet): Unit = {
        promise success result
      }

      def onFailure(err: Throwable): Unit = {
        promise failure err
      }
    }

    Futures.addCallback(f, callback)
    promise.future
  }
}
