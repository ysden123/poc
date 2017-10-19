package com.stulsoft.pakka.basics.general.example1

import com.stulsoft.pakka.basics.general.example1.actors.HelloWorldActor

/**
  * Created by Yuriy Stul on 10/3/2016.
  */

/**
  * This is actually just a small wrapper around the generic launcher
  * class akka.Main, which expects only one argument: the class name of
  * the application?s main actor. This main method will then create the
  * infrastructure needed for running the actors, start the given main
  * actor and arrange for the whole application to shut down once the main
  * actor terminates.
  *
  * Thus you could also run the application with a
  * command similar to the following:
  * java -classpath  akka.Main com.example.actors.HelloWorldActor
  */
object Hello {

  def main(args: Array[String]): Unit = {
    val initialActor = classOf[HelloWorldActor].getName
    akka.Main.main(Array(initialActor))
  }
}
