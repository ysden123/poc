/*
 * Copyright (c) 2018. Yuriy Stul
 */

package com.stulsoft.pom.experiments.application.app3;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.AbstractEnvironment;

import javax.inject.Inject;

/**
 * @author Yuriy Stul
 */
@SpringBootApplication
public class Application implements CommandLineRunner {
    @Inject
    private AbstractEnvironment environment;

    /**
     * Callback used to run the bean.
     *
     * @param args incoming main method arguments
     * @throws Exception on error
     */
    @Override
    public void run(String... args) throws Exception {
        System.out.println("==>run App3");
        System.out.printf("environment.getProperty(\"test.name\"): %s%n", environment.getProperty("test.name"));
        environment.getPropertySources().forEach(ps -> {
            System.out.printf("%s%n", ps.getName());
        });
        System.out.println("<==run  App3");
    }
}
