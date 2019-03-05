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
public class CallableWithErrorRunner {
    private static Logger logger = LoggerFactory.getLogger(CallableWithErrorRunner.class);

    public static void main(String[] args) {
        logger.info("==>main");
        try {
            ExecutorService executorService = Executors.newSingleThreadExecutor();

            try {
                String text = "low case";
                Future<String> result = executorService.submit(new CallableWithError(text));
                logger.info("Result for {} is {}", text, result.get());
            }catch (Exception ex){
                logger.error("Exception 2 " + ex.getMessage(), ex);
            }finally {
                executorService.shutdown();
            }
        } catch (Exception ex) {
            logger.error("Exception 2 " + ex.getMessage(), ex);
        }
        logger.info("<==main");
    }

}
