/*
 * Copyright (c) 2019. Yuriy Stul
 */

package com.stulsoft.java7.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;

/**
 * @author Yuriy Stul
 */
public interface RecordHandler {
    String topic();
    void handle(ConsumerRecord<String,String> record);
}
