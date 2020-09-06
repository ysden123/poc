/*
 * Copyright (c) 2020. Yuriy Stul
 */

package com.stulsoft.json

import com.typesafe.scalalogging.StrictLogging

/**
 * @author Yuriy Stul
 */
object T2 extends App with StrictLogging {
  f(List(1, 2, 3))
  f(List(Map("a"->1),Map("2"->1)))

  def f(a: Any): Unit = {
    logger.debug("a: {}", a)
    a match {
      case m: Map[String, Any] =>
        logger.debug("a is Map[String, Any]")
      case l:List[Any] =>
        logger.debug("a is List[Any]")
        logger.debug("l: {}", l)
      case l: List[Map[String, Any]] =>
        logger.debug("a is List[Map[String,Any]]")
        l.foreach(i=>logger.debug("i:{}", i))
      case x =>
        logger.debug("a is unsupported")
    }
  }

  def f2(a: Any): Unit = {
    a match {
      case m: Map[String, Any] =>
        logger.debug("a is Map[String, Any]")
      case l: List[Map[String, Any]] =>
        logger.debug("a is List[Map[String,Any]]")
      case l2: List[Any] =>
        logger.debug("a is List[Any]")
      case x =>
        logger.debug("a is unsupported")
    }
  }
}
