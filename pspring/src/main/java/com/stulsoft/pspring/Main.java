/*
 * Copyright (c) 2017, William Hill Online. All rights reserved
 */
package com.stulsoft.pspring;

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
    public static void main(String[] args) {
        System.out.println("==>main");
        SpringApplication.run(Main.class, args);
        System.out.println("<==main");
    }

    @Bean
    CommandLineRunner commandLineRunner(ApplicationContext ctx) {
        return args -> {
            System.out.println("==>commandLineRunner");
            Manager manager = ctx.getBean(Manager.class);
            manager.userService();
            System.out.println("<==commandLineRunner");
        };
    }
}
