/*
 * Copyright (c) 2019. Yuriy Stul
 */

package com.stulsoft.java7.concurrency;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Callable;

/**
 * @author Yuriy Stul
 */
public class CallableWithError implements Callable<String> {
    private static Logger logger = LoggerFactory.getLogger(CallableWithError.class);
    private String text;

    CallableWithError(String text) {
        this.text = text;
    }

    /**
     * Computes a result, or throws an exception if unable to do so.
     *
     * @return computed result
     * @throws Exception if unable to compute a result
     */
    @Override
    public String call() {
        logger.info("Running with {}", text);
        throw new RuntimeException("Test exception");
    }
}
