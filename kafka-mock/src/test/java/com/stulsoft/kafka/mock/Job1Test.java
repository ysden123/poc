package com.stulsoft.kafka.mock;

import org.apache.kafka.clients.producer.MockProducer;
import org.apache.kafka.clients.producer.Producer;
import org.junit.Test;

/**
 * @author Yuriy Stul.
 */
public class Job1Test {
    private static final boolean AUTO_COMPLETE = true;
    private static final boolean NO_AUTO_COMPLETE = false;

    @Test
    public void f1Normal() {
        Producer<String, String> producer = new MockProducer<>(AUTO_COMPLETE, null, null);
        Job1 job1 = new Job1(producer);
        job1.f1();
    }

    @Test(expected = RuntimeException.class)
    public void f1Fail() {
        MockProducer<String, String> producer = new MockProducer<>(NO_AUTO_COMPLETE, null, null);
        producer.errorNext(new RuntimeException("The test exception"));
        Job1 job1 = new Job1(producer);
        job1.f1();
    }
}