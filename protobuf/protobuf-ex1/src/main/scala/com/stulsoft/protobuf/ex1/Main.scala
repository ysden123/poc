/*
 * Copyright (c) 2019. Yuriy Stul
 */

package com.stulsoft.protobuf.ex1

import java.io.ByteArrayOutputStream

import com.stulsoft.protobuf.ex1.model.{Address, Person}
import com.stulsoft.protobuf.ex1.model2.Developer
import com.typesafe.scalalogging.LazyLogging

/**
 * @author Yuriy Stul
 */
object Main extends App with LazyLogging {
  logger.info("==>Main")

  test1()
  test2()
  test3()
  test4()

  def test1(): Unit = {
    logger.info("==>test1")
    val address = Address("Some street", "Some city")
    val person = Person("Some name", 21, List(address))
    logger.info(s"Person: $person")
    logger.info("<==test1")
  }

  def test2(): Unit = {
    logger.info("==>test2")
    val address = Address("Some street", "Some city")
    val out = new ByteArrayOutputStream()
    address.writeTo(out)
    out.close()

    val address2 = Address.parseFrom(out.toByteArray)
    logger.info(s"address2: $address2")

    logger.info("<==test2")
  }

  def test3(): Unit ={
    logger.info("==>test3")
    val address1 = Address("Some street")
    logger.info(s"address1: $address1")

    val person1 = Person("Some name")
    logger.info(s"person1: $person1")

    val person2 = Person("Some name").withAddresses(Seq(address1))
    logger.info(s"person2: $person2")

    logger.info("<==test3")
  }

  def test4(): Unit ={
    logger.info("==>test4")
    val developer = Developer("Yuriy")
    logger.info(s"developer: $developer")
    logger.info("<==test4")
  }
}
