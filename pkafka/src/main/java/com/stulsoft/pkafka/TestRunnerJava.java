/*
 * Copyright (c) 2016. Yuriy Stul
 */

package com.stulsoft.pkafka;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Executes producer and consumer.
 *
 * @author Yuriy Stul
 */
public class TestRunnerJava {
    /**
     * Test runner
     *
     * @param args arguments (not used)
     */
    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(2);
        // Start consumer
        executor.submit(() -> Consumer1.readMessages(2));

        // Start producer
        executor.submit(Producer1::sendMessages);

        try {
            Thread.sleep(5000);
            System.exit(0);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
