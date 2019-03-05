/*
 * Copyright (c) 2019. Yuriy Stul
 */

package com.stulsoft.java7.kafka;

import com.sun.org.apache.xpath.internal.functions.Function3Args;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.KafkaFuture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Properties;

/**
 * @author Yuriy Stul
 */
public class KafkaClient {
    private static Logger logger = LoggerFactory.getLogger(KafkaClient.class);

    private KafkaConsumer<String, String> consumer;
    private HashMap<String, RecordHandler> handlers = new HashMap<>();

    KafkaClient() {
        logger.info("==>KafkaClient");
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("group.id", "test");
        props.put("enable.auto.commit", "true");
        props.put("auto.offset.reset", "latest");
        props.put("auto.commit.interval.ms", "1000");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

        consumer = new KafkaConsumer<>(props);

//        consumer.subscribe(Collections.singleton("test1Topic"));
        logger.info("<==KafkaClient");
    }

    void addHandler(RecordHandler handler) {
        logger.debug("Adding handler for {}", handler.topic());
        handlers.put(handler.topic(), handler);
    }

    void run() {
        logger.debug("handlers.keySet(): {}", handlers.keySet());
        consumer.subscribe(handlers.keySet());

        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(100);
            for (ConsumerRecord<String, String> record : records) {
                RecordHandler handler = handlers.get(record.topic());
                if (handler != null) {
                    handler.handle(record);
                } else {
                    logger.error("No handler found for topic {}", record.topic());
                }
            }
        }
    }
}
