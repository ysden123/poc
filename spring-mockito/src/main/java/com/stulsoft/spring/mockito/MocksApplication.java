/*
 * Copyright (c) 2017. Yuriy Stul
 */

package com.stulsoft.spring.mockito;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

/**
 * @author Yuriy Stul
 * @see <a href="http://www.baeldung.com/injecting-mocks-in-spring">Injecting Mockito Mocks into Spring Beans</a>
 */
@SpringBootApplication
public class MocksApplication {

    public static void main(String[] args) {
        System.out.println("==>main");
        SpringApplication.run(MocksApplication.class, args);
        System.out.println("<==main");
    }

    @Bean
    CommandLineRunner commandLineRunner(ApplicationContext ctx) {
        return args -> {
            System.out.println("==>commandLineRunner");
            UserService userService = ctx.getBean(UserService.class);
            System.out.println("userService.getUserName(\"the id\") = " + userService.getUserName("the id"));
            System.out.println("<==commandLineRunner");
        };
    }

}
