/*
 * Copyright (c) 2019. Yuriy Stul
 */

package com.stulsoft.java7.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;

/**
 * @author Yuriy Stul
 */
public abstract class AHandleTask implements Runnable{
    protected ConsumerRecord<String, String> record;

    AHandleTask(ConsumerRecord<String, String> record) {
        this.record = record;
    }
}
