/*
 * Copyright (c) 2023. StulSoft
 */

package com.stulsoft.sound

import com.typesafe.scalalogging.StrictLogging

import java.io.ByteArrayInputStream
import javax.sound.sampled.AudioSystem
import scala.concurrent.{ExecutionContext, Future}
import scala.util.{Failure, Success}

object Sound extends StrictLogging:
  given ExecutionContext = ExecutionContext.global

  def playFanfare(): Unit =
    logger.info("==>playFanfare")
    Future {
      BinaryDataReader.readBinaryDataFromResource("sounds/fanfare.wav") match
        case Success(inputStream) =>
          logger.info("Data was read")
          try
            val clip = AudioSystem.getClip()
            clip.open(AudioSystem.getAudioInputStream(inputStream))
            clip.start()
          catch
            case exception: Exception =>
              logger.error(exception.getMessage, exception)
        case Failure(exception) =>
          logger.error("Data was not read")
    }
