/*
 * Copyright (c) 2017. Yuriy Stul
 */

package com.stulsoft.simple.cassandra

import com.datastax.driver.core._
import com.datastax.driver.core.querybuilder.{QueryBuilder => QB}

import com.stulsoft.simple.cassandra.Utils._

import scala.collection.JavaConverters._
import scala.concurrent._

/**
  * @see [[http://eax.me/scala-cassandra/]]
  * @author Yuriy Stul
  */
case class TodoDTO(id: Int, descr: String)

case class TodoDAO(session: Session)(implicit ec: ExecutionContext) {

  private val table = "todo_list"
  private val id = "id"
  private val description = "description"

  def createTable: Future[Unit] = {
    val query = s"create table if not exists $table ($id int primary " +
      s"key, $description text )"
    session.executeAsync(query).map(_ => {})
  }

  def dropTable: Future[Unit] = {
    val query = s"drop table if exists $table"
    session.executeAsync(query).map(_ => {})
  }

  def insert(dto: TodoDTO): Future[Unit] = {
    val query = {
      QB.insertInto(table)
        .value(id, dto.id)
        .value(description, dto.descr)
    }
    session.executeAsync(query).map(_ => {})
  }

  def select: Future[Seq[TodoDTO]] = {
    val query = {
      QB.select(id, description)
        .from(table)
    }

    for {
      resultSet <- session.executeAsync(query)
    } yield {
      resultSet
        .asScala
        .map(row => TodoDTO(row.getInt(id), row.getString(description)))
        .toSeq
    }
  }

  def delete(idToDelete: Long): Future[Unit] = {
    val query = {
      QB.delete().all()
        .from(table)
        .where(QB.eq(id, idToDelete))
    }
    session.executeAsync(query).map(_ => {})
  }
}