/*
 * Copyright (c) 2019. Yuriy Stul
 */

package com.stulsoft.kafkaj;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.Properties;

/**
 * @author Yuriy Stul
 */
public class SimpleConsumer {
    private static Logger logger = LoggerFactory.getLogger(SimpleConsumer.class);

    public static void main(String[] args) {
        logger.info("==>main");

        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("group.id", "test");
        props.put("enable.auto.commit", "true");
        props.put("auto.offset.reset", "latest");
        props.put("auto.commit.interval.ms", "1000");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);

        consumer.subscribe(Collections.singleton("test1Topic"));

        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(100);
            for (ConsumerRecord<String, String> record : records) {
                String result = String.format("Received message. Partition = %d, offset is %d, topic is %s, key is %s, value is %s",
                        record.partition(), record.offset(), record.topic(), record.key(), record.value());
                logger.info(result);
            }
            consumer.commitSync();
        }
    }
}
