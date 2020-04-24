/*
 * Copyright (c) 2020. Yuriy Stul
 */

package com.stulsoft.kafka.mock;

import org.apache.kafka.clients.producer.MockProducer;
import org.apache.kafka.clients.producer.Producer;
import org.junit.Test;

/**
 * @author Yuriy Stul.
 */
public class JobWithProducerTest {
    private static final boolean AUTO_COMPLETE = true;
    private static final boolean NO_AUTO_COMPLETE = false;

    @Test
    public void f1Normal() {
        Producer<String, String> producer = new MockProducer<>(AUTO_COMPLETE, null, null);
        JobWithProducer jobWithProducer = new JobWithProducer(producer);
        jobWithProducer.f1();
    }

    @Test(expected = RuntimeException.class)
    public void f1Fail() {
        MockProducer<String, String> producer = new MockProducer<>(NO_AUTO_COMPLETE, null, null);
        producer.errorNext(new RuntimeException("The test exception"));
        JobWithProducer jobWithProducer = new JobWithProducer(producer);
        jobWithProducer.f1();
    }
}