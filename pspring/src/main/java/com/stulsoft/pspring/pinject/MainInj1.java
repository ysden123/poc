/*
   Created by Yuriy Stul 2018
*/
package com.stulsoft.pspring.pinject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MainInj1 {
    @Autowired
    Service1 service1;

    public static void main(String[] args) {
        SpringApplication.run(MainInj1.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(ApplicationContext ctx) {
        return args -> {
            Configuration1 configuration1 = ctx.getBean(Configuration1.class);
            configuration1.service1().print();
            service1.print();
        };
    }
}