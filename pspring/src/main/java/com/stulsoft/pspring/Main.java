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

/**
 * @author Yuriy Stul.
 */
@SpringBootApplication
public class Main {
    static Logger logger = LoggerFactory.getLogger(Manager.class);
    public static void main(String[] args) {
        logger.debug("==>main");
        SpringApplication.run(Main.class, args);
        logger.debug("<==main");
    }

    @Bean
    CommandLineRunner commandLineRunner(ApplicationContext ctx) {
        return args -> {
            logger.debug("==>commandLineRunner");
            Manager manager = ctx.getBean(Manager.class);
            manager.userService();

            Manager2 manager2 = ctx.getBean(Manager2.class);
            manager2.userService();

            System.out.println("\nClassWithValueAnnotation:");
            ClassWithValueAnnotation cwv = ctx.getBean(ClassWithValueAnnotation.class);
            System.out.println("value1=" + cwv.getValue1());
            System.out.println("value2=" + cwv.getValue2());
            System.out.println("javaHome=" + cwv.getJavaHome());

            System.out.println("\nClassWithValueAnnotation2:");
            ClassWithValueAnnotation2 cwv2 = new ClassWithValueAnnotation2();
            System.out.println("value1=" + cwv2.getValue1());
            System.out.println("value2=" + cwv2.getValue2());
            System.out.println("javaHome=" + cwv2.getJavaHome());

            logger.debug("<==commandLineRunner");
        };
    }
}
