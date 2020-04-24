/*
 * Copyright (c) 2020. Yuriy Stul
 */

package com.stulsoft.kafka.mock;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.MockConsumer;
import org.apache.kafka.clients.consumer.OffsetResetStrategy;
import org.apache.kafka.common.TopicPartition;
import org.junit.Test;

import java.util.Collections;
import java.util.HashMap;

/**
 * @author Yuriy Stul
 */
public class JobWithConsumerTest {
    @Test
    public void f1() {
        MockConsumer<String, String> consumer = new MockConsumer<>(OffsetResetStrategy.EARLIEST);
        consumer.assign(Collections.singletonList(new TopicPartition(JobWithConsumer.topic, 0)));
        HashMap<TopicPartition, Long> beginningOffsets = new HashMap<>();
        beginningOffsets.put(new TopicPartition(JobWithConsumer.topic, 0), 0L);

        consumer.updateBeginningOffsets(beginningOffsets);
        consumer.addRecord(new ConsumerRecord<>(JobWithConsumer.topic,
                0, 0L, "key", "value1"));
        consumer.addRecord(new ConsumerRecord<>(JobWithConsumer.topic,
                0, 1L, "key", "value2"));

        var t = new JobWithConsumer(consumer);
        t.f1();
    }
}