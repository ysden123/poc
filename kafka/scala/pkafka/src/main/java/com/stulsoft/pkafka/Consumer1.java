/*
 * Copyright (c) 2016. Yuriy Stul
 */

package com.stulsoft.pkafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.Properties;

/**
 * Consumer
 *
 * @author Yuriy Stul
 */
public class Consumer1 {
    private static final Logger logger = LoggerFactory.getLogger(Consumer1.class);

    public static void main(String[] args) {
        readMessages(10);
    }

    /**
     * Reads messages
     */
    static void readMessages(int numOfMessages) {
        if (!CheckConnection.checkConnection()) {
            logger.error("Kafka server is unavailable.");
            System.exit(1);
        }

        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("group.id", "test");
        props.put("enable.auto.commit", "true");
        props.put("auto.commit.interval.ms", "1000");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
        consumer.subscribe(Collections.singletonList("my-topic"));
        int msgCount = 0;
        while (msgCount < numOfMessages) {
            ConsumerRecords<String, String> records = consumer.poll(100);
            for (ConsumerRecord<String, String> record : records) {
                logger.info(String.format("offset = %d, key = %s, value = %s%n", record.offset(), record.key(), record.value()));
                ++msgCount;
            }
            consumer.commitSync();
        }

        logger.info("Received {} messages", msgCount);
        consumer.unsubscribe();
        consumer.close();
    }
}
