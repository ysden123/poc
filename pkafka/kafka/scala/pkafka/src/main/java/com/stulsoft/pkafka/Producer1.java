/*
 * Copyright (c) 2016. Yuriy Stul
 */

package com.stulsoft.pkafka;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * Producer
 *
 * @author Yuriy Stul
 */
public class Producer1 {
    private static final Logger logger = LoggerFactory.getLogger(Producer1.class);

    public static void main(String[] args) {
        sendMessages();
    }

    /**
     * Sends tests messages
     */
    static void sendMessages() {
        if (!CheckConnection.checkConnection()) {
            logger.error("Kafka server is unavailable.");
            System.exit(1);
        }

        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("acks", "all");
        props.put("retries", 0);
        props.put("batch.size", 16384);
        props.put("linger.ms", 1);
        props.put("buffer.memory", 33554432);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        Producer<String, String> producer = new KafkaProducer<>(props);
        List<Future<RecordMetadata>> futures = new ArrayList<>();

        for (int i = 0; i < 2; i++) {
            try {
                logger.info("Send message No.{}", i);
                Future<RecordMetadata> future = producer.send(new ProducerRecord<>("my-topic", Integer.toString(i), Integer.toString(i)));
                futures.add(future);
            } catch (Throwable t) {
                logger.error("Error {}", t.getMessage());
            }
        }

        logger.info("Sent {} messages", futures.size());

        futures.forEach(f -> {
            try {
                RecordMetadata result = f.get(1, TimeUnit.SECONDS);
                logger.info("Succeeded send message {}", result);
            } catch (Exception e) {
                logger.error("Failed send {}. Error: {} ", f, e.getMessage());
            }
        });

        logger.info("All done");

        producer.close();
    }
}
