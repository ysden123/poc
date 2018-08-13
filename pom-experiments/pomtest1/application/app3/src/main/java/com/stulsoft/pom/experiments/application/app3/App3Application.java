/*
 * Copyright (c) 2018. Yuriy Stul
 */

package com.stulsoft.pom.experiments.application.app3;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.AbstractEnvironment;
import org.springframework.core.env.MutablePropertySources;

import javax.inject.Inject;

/**
 * @author Yuriy Stul
 */
@SpringBootApplication
public class App3Application implements CommandLineRunner {
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
        System.out.println("Properties:");
        MutablePropertySources mps = environment.getPropertySources();
        mps.forEach(ps -> {
            System.out.printf("Property source name: %s%n", ps.getName());
        });
        System.out.printf("environment.getProperty(\"test.name\"): %s%n", environment.getProperty("test.name"));
        System.out.println("<==run  App3");
    }
}
