package com.stulsoft.pkafka.scala.stream

import org.apache.kafka.streams.KeyValue

/**
  * Implicit conversions that provide us with some syntactic sugar when writing stream transformations.
  *
  * @see [[https://github.com/confluentinc/examples/blob/3.2.x/kafka-streams/src/main/scala/io/confluent/examples/streams/KeyValueImplicits.scala original code]]
  * @author Yuriy Stul
  */
object KeyValueImplicits {
  implicit def Tuple2ToKeyValue[K, V](tuple: (K, V)): KeyValue[K, V] = new KeyValue(tuple._1, tuple._2)
}
