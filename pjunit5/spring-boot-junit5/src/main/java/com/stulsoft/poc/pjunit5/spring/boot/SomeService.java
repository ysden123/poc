/*
 * Copyright (c) 2018. Yuriy Stul
 */

package com.stulsoft.poc.pjunit5.spring.boot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author Yuriy Stul
 */
@Component
public class SomeService {
    private static final Logger logger = LoggerFactory.getLogger(SomeService.class);

    public void doServiceWork(){
        logger.info("==>doServiceWork");
        logger.info("<==doServiceWork");
    }
}