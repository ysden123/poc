/*
 * Copyright (c) 2019. Yuriy Stul
 */

package com.stulsoft.java7.kafka.agent;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Yuriy Stul
 */
public class Handler1 implements Runnable {
    private Logger logger = LoggerFactory.getLogger(Handler1.class);

    private ConsumerRecord<String, String> record;

    public Handler1(final ConsumerRecord<String, String> record) {
        this.record = record;
    }

    /**
     * When an object implementing interface <code>Runnable</code> is used
     * to create a thread, starting the thread causes the object's
     * <code>run</code> method to be called in that separately executing
     * thread.
     * <p>
     * The general contract of the method <code>run</code> is that it may
     * take any action whatsoever.
     *
     * @see Thread#run()
     */
    @Override
    public void run() {
        logger.info("==>run");
        logger.info("Handling message. Topic: {}, offset = {}, partition = {}, key: {}, value: {}",
                record.topic(), record.offset(), record.partition(), record.key(), record.value());
        logger.info("<==run");
    }
}
