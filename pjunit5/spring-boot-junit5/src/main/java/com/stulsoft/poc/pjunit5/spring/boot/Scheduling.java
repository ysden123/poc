/*
 * Copyright (c) 2018. Yuriy Stul
 */

package com.stulsoft.poc.pjunit5.spring.boot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * @author Yuriy Stul
 */
@Configuration
@ConditionalOnProperty(
        value = "app.scheduling.enable", havingValue = "true", matchIfMissing = true
)
@EnableScheduling
public class Scheduling {
    private static final Logger logger = LoggerFactory.getLogger(Scheduling.class);

    @Autowired
    private SomeComponent someComponent;

    @Scheduled(fixedDelay = 2000)
    public void scheduleFixedDelay() {
        logger.info("==>scheduleFixedDelay");
        someComponent.work();
        logger.info("<==scheduleFixedDelay");
    }
}
