/*
 * Copyright (c) 2019. Yuriy Stul
 */

package com.stulsoft.kafkaj;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;
import java.util.Random;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * @author Yuriy Stul
 */
public class SimpleProducer {
    private static Logger logger = LoggerFactory.getLogger(SimpleProducer.class);
    private static Random random = new Random();

    public static void main(String[] args) {
        logger.info("==>main");
        boolean continueExecuting = true;
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("acks", "all");
        props.put("retries", 0);
        props.put("batch.size", 16384);
        props.put("linger.ms", 1);
        props.put("buffer.memory", 33554432);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        KafkaProducer<String, String> producer = new KafkaProducer<>(props);

        while (continueExecuting) {
            sendMessage(producer);
            try {
                Thread.sleep(1000);
            } catch (Exception ignore) {
            }
        }
    }

    private static void sendMessage(KafkaProducer<String, String> producer) {
        try {
            Future<RecordMetadata> future = producer.send(new ProducerRecord<>("test1Topic",
                    "theKey_" + random.nextInt(),
                    "the value_" + random.nextInt()));
            try {
                RecordMetadata result = future.get(10, TimeUnit.SECONDS);
                String resultText = String.format("Succeeded snd message. Offset is %d, partition is %d, topic is %s",
                        result.offset(), result.partition(), result.topic());
                logger.info(resultText);
            } catch (Exception ex) {
                logger.error(ex.getMessage());
            }
        } catch (Exception ex) {
            logger.error("Failed sending {}", ex.getMessage());
        }
    }
}
