/*
 * Copyright (c) 2018, William Hill Online. All rights reserved
 */
package com.stulsoft.kafka.mock;

import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * @author Yuriy Stul.
 */
public class Job1 {
    private static Logger logger = LoggerFactory.getLogger(Job1.class);
    private static String topic = "test-topic-job1";
    private final Producer<String, String> producer;

    Job1(final Producer<String, String> producer) {
        this.producer = producer;
    }

    public void f1() {
        logger.info("==>f1");
        ProducerRecord<String, String> record = new ProducerRecord<>(topic, "key", "value");
        Future<RecordMetadata> result = producer.send(record);
        try {
            RecordMetadata metadata = result.get(100, TimeUnit.MILLISECONDS);
            logger.info("offset={}", metadata.offset());
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
            throw new RuntimeException(e.getMessage());
        }
        logger.info("<==f1");
    }
}
