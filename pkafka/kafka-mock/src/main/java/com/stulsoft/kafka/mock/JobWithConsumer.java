/*
 * Copyright (c) 2020. Yuriy Stul
 */

package com.stulsoft.kafka.mock;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.common.TopicPartition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.Collections;

/**
 * For testing consumer
 *
 * @author Yuriy Stul
 */
public class JobWithConsumer {
    private static final Logger logger = LoggerFactory.getLogger(JobWithConsumer.class);
    public static final String topic = "test-topic-job2";
    private final Consumer<String, String> consumer;

    public JobWithConsumer(Consumer<String, String> consumer) {
        this.consumer = consumer;
    }

    public void f1() {
        logger.info("==>f1");

        consumer.assign(Collections.singletonList(new TopicPartition(topic, 0)));
        var records = consumer.poll(Duration.ofMillis(1000));
        logger.debug("records.count()={}", records.count());
        records.forEach(record -> logger.debug("{}", record.value()));
        logger.info("<==f1");
    }
}
