/*
 * Copyright (c) 2017, William Hill Online. All rights reserved
 */
package com.stulsoft.kafkaj;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @author Yuriy Stul.
 */
public class ConsumerEarliestCommitAllNoCommitRunner {
    private static Logger logger = LoggerFactory.getLogger(ConsumerEarliestCommitAllNoCommitRunner.class);

    public static void main(String[] args) {
        logger.info("==>ConsumerEarliestCommitAllRunner");
        ExecutorService executor= Executors.newFixedThreadPool(4);
        String topic = "main1TestTopic";
        Consumer consumer = new CommonConsumer(executor, "test", AutoCommit.DisabledAutoCommit, AutoOffsetRest.Earliest, Commit.NoCommit, topic);
        Future<Void> startedConsumer = consumer.start();
        System.out.println("Enter line to exit...");
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
        scanner.close();
        logger.info("Stopping consumer...");
        try {
            consumer.stop().get();
        } catch (Exception ignore) {
        }
        try {
            startedConsumer.get();
        } catch (Exception ignore) {
        }
        logger.info("==<ConsumerEarliestCommitAllRunner");
    }
}
