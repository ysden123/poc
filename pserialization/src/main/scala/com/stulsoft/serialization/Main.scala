package com.stulsoft.serialization

import java.io.{FileInputStream, FileOutputStream, ObjectInputStream, ObjectOutputStream}

/**
  * @author Yuriy Stul.
  */
object Main extends App {
  test1()
  test2()
  test3()
  test4()

  def test1(): Unit = {
    println("==>test1")
    val msg = new Message("test")
    val outputStream = new ObjectOutputStream(new FileOutputStream("test.dat"))
    outputStream.writeObject(msg)
    outputStream.close()

    val inputStream = new ObjectInputStream(new FileInputStream("test.dat"))
    val msgRestored = inputStream.readObject.asInstanceOf[Message]
    inputStream.close()

    println(msgRestored)
    println("<==test1")
  }

  def test2(): Unit = {
    println("==>test2")
    val msg = Message2("test")
    val outputStream = new ObjectOutputStream(new FileOutputStream("test2.dat"))
    outputStream.writeObject(msg)
    outputStream.close()

    val inputStream = new ObjectInputStream(new FileInputStream("test2.dat"))
    val msgRestored = inputStream.readObject.asInstanceOf[Message2]
    inputStream.close()

    println(msgRestored)
    println("<==test2")
  }

  def test3(): Unit ={
    println("==>test3")
    val msg = Message3("test")
    val outputStream = new ObjectOutputStream(new FileOutputStream("test3.dat"))
    outputStream.writeObject(msg)
    outputStream.close()

    val inputStream = new ObjectInputStream(new FileInputStream("test3.dat"))
    val msgRestored = inputStream.readObject.asInstanceOf[MessageTrait]
    inputStream.close()

    println(msgRestored)
    println("<==test3")
  }

  def test4(): Unit ={
    println("==>test4")
    val msg3 = Message3("test")
    val msg4 = Message4("test", 123)
    val outputStream = new ObjectOutputStream(new FileOutputStream("test4.dat"))
    outputStream.writeObject(msg3)
    outputStream.writeObject(msg4)
    outputStream.close()

    val inputStream = new ObjectInputStream(new FileInputStream("test4.dat"))
    val msgRestored3 = inputStream.readObject.asInstanceOf[MessageTrait]
    val msgRestored4 = inputStream.readObject.asInstanceOf[MessageTrait]

    inputStream.close()
    println(msgRestored3)
    println(msgRestored4)
    println("<==test4")
  }
}
