/*
 * Copyright (c) 2021. StulSoft
 */

package com.stulsoft.poc.gradle.spring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

/**
 * @author Yuriy Stul
 */
@Configuration
public class AppConfiguration {
    private static final Logger logger = LoggerFactory.getLogger(AppConfiguration.class);

    @Bean(name = "abc")
    public String abc(@Autowired Environment env) {
        logger.info("==>abc");
        return env.getProperty("a.b.c");
    }
}
