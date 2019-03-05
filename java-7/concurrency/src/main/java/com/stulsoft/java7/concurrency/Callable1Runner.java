/*
 * Copyright (c) 2019. Yuriy Stul
 */

package com.stulsoft.java7.concurrency;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @author Yuriy Stul
 */
public class Callable1Runner {
    private static Logger logger = LoggerFactory.getLogger(Callable1Runner.class);

    public static void main(String[] args) {
        logger.info("==>main");
        try {
            ExecutorService executorService = Executors.newSingleThreadExecutor();

            String text = "low case";
            Future<String> result = executorService.submit(new Callable1(text));
            logger.info("Result for {} is {}", text, result.get());
            executorService.shutdown();
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        }
        logger.info("<==main");
    }

}
