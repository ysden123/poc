/*
 * Copyright (c) 2019. Yuriy Stul
 */

package com.stulsoft.java7.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Yuriy Stul
 */
public class Handler1 implements RecordHandler {
    private static Logger logger = LoggerFactory.getLogger(Handler1.class);

    private String topic;

    Handler1(String topic) {
        this.topic = topic;
    }

    @Override
    public String topic() {
        return topic;
    }

    @Override
    public void handle(ConsumerRecord<String, String> record) {
        logger.info("Received message. Partition: {}, offset = {}, topic: {}, key: {}, value: {}",
                record.partition(),
                record.offset(),
                record.topic(),
                record.key(),
                record.value());
    }
}
