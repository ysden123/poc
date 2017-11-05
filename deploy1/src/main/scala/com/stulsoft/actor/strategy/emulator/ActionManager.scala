/*
 * Copyright (c) 2017. Yuriy Stul
 */

package com.stulsoft.actor.strategy.emulator

/**
  * @author Yuriy Stul
  */
object ActionManager {
  private val actionMap = scala.collection.mutable.Map[String, List[AnyRef]]()

  def buildActions(name: String, actions: List[AnyRef]): Unit = {
    actionMap.synchronized {
      actionMap += (name -> actions)
    }
  }

  def nextAction(name: String): Option[AnyRef] = {
    actionMap.synchronized {
      val actions = actionMap(name)
      if (actions.nonEmpty) {
        val action = actions.head
        actionMap(name) = actions.tail
        Some(action)
      } else
        None
    }
  }
}
