package com.stulsoft.serialization

/**
  * @author Yuriy Stul.
  */
@SerialVersionUID(123L)
class Message(val content: String) extends Serializable {
  override def toString: String = s"Message[content=$content]"
}
