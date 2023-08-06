/*
 * Copyright (c) 2023. StulSoft
 */

package com.stulsoft.sound

import com.typesafe.scalalogging.StrictLogging

object Main extends StrictLogging:
  def main(args: Array[String]): Unit =
    logger.info("==>main")
    Sound.playFanfare()

    Thread.sleep(5000)
