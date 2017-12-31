/*
 * Copyright (c) 2017, William Hill Online. All rights reserved
 */
package com.stulsoft.kafkaj;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Scanner;
import java.util.concurrent.Future;

/**
 * @author Yuriy Stul.
 */
public class CommonConsumerRunner {
    private static Logger logger = LoggerFactory.getLogger(CommonConsumerRunner.class);

    public static void main(String[] args) {
        logger.info("==>CommonConsumerRunner");
        String topic = "main1TestTopic";
        Consumer consumer = new CommonConsumer("test", AutoCommit.DisabledAutoCommit, AutoOffsetRest.Latest, Commit.Commit, topic);
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
        logger.info("==<CommonConsumerRunner");
    }
}
