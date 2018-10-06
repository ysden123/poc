/*
 * Copyright (c) 2018. Yuriy Stul
 */

package com.stulsoft.poc.pjunit5.spring.boot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Yuriy Stul
 */
@Component
public class SomeComponent {
    private static final Logger logger = LoggerFactory.getLogger(SomeComponent.class);

    private final SomeService someService;

    public SomeComponent(@Autowired SomeService someService) {
        this.someService = someService;
    }

    public void work() {
        logger.info("==>work");
        someService.doServiceWork();
        logger.info("<==work");
    }
}
