/*
 * Copyright (c) 2021. StulSoft
 */

package com.stulsoft.poc.gradle.spring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.AbstractEnvironment;

/**
 * @author Yuriy Stul
 */
@SpringBootApplication
public class Application implements CommandLineRunner {
    private static final Logger logger = LoggerFactory.getLogger(Application.class);

    private final String abc;

    public Application(@Autowired final String abc) {
        this.abc = abc;
    }

    public static void main(String[] args) {
        logger.info("==>main");
        System.setProperty(AbstractEnvironment.DEFAULT_PROFILES_PROPERTY_NAME, "dev");
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... args) {
        logger.info("==>run");
        logger.info("abc={}", abc);
    }
}
