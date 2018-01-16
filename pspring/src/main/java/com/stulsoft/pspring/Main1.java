/*
 * Copyright (c) 2017, William Hill Online. All rights reserved
 */
package com.stulsoft.pspring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;

/**
 * @author Yuriy Stul.
 */
@SpringBootApplication
@ImportResource("classpath:beans1.xml")
public class Main1 {
    private static Logger logger = LoggerFactory.getLogger(Main1.class);
    public static void main(String[] args) {
        logger.debug("==>main");
        SpringApplication.run(Main1.class, args);
        logger.debug("<==main");
    }

    @Bean
    CommandLineRunner commandLineRunner(ApplicationContext ctx) {
        return args -> {
            logger.debug("==>commandLineRunner");
            Manager3 manager3 = ctx.getBean(Manager3.class);
            manager3.userService();
            logger.debug("<==commandLineRunner");
        };
    }
}
