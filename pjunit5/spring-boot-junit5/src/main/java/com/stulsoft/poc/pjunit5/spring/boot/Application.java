/*
 * Copyright (c) 2018. Yuriy Stul
 */

package com.stulsoft.poc.pjunit5.spring.boot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Yuriy Stul
 */
@SpringBootApplication(scanBasePackages = {"com.stulsoft.poc.pjunit5.spring.boot"})
public class Application implements CommandLineRunner {
    private static final Logger logger = LoggerFactory.getLogger(Application.class);

    @Override
    public void run(String... args) {
        logger.info("==>run");
        logger.info("<==run");
    }
}
