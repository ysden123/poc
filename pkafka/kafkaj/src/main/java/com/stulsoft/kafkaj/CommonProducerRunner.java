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
public class CommonProducerRunner {
    private static Logger logger = LoggerFactory.getLogger(CommonProducerRunner.class);

    public static void main(String[] args) {
        logger.info("==>CommonProducerRunner");
        String topic = "main1TestTopic";
        ExecutorService executor = Executors.newFixedThreadPool(4);
        Producer producer = new CommonProducer(executor, topic, 1500);
        Future<Void> startedProducer = producer.start();
        System.out.println("Enter line to exit...");
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
        scanner.close();
        logger.info("Stopping producer...");
        try {
            producer.stop().get();
        } catch (Exception ignore) {
        }
        try {
            startedProducer.get();
        } catch (Exception ignore) {
        }
        logger.info("<==CommonProducerRunner");
    }
}
