/*
 * Copyright (c) 2019. Yuriy Stul
 */

package com.stulsoft.java7.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;

/**
 * @author Yuriy Stul
 */
public class Handler1 implements RecordHandler {
    private static Logger logger = LoggerFactory.getLogger(Handler1.class);

    class HandleTask implements Runnable {
        private ConsumerRecord<String, String> record;

        HandleTask(ConsumerRecord<String, String> record) {
            this.record = record;
        }

        @Override
        public void run() {
            logger.info("Received message. Partition: {}, offset = {}, topic: {}, key: {}, value: {}",
                    record.partition(),
                    record.offset(),
                    record.topic(),
                    record.key(),
                    record.value());
        }
    }

    private String topic;
    private ExecutorService es;

    Handler1(String topic, ExecutorService es) {
        this.topic = topic;
        this.es = es;
    }

    @Override
    public String topic() {
        return topic;
    }

    @Override
    public void handle(ConsumerRecord<String, String> record) {
        es.submit(new HandleTask(record));
    }
}
