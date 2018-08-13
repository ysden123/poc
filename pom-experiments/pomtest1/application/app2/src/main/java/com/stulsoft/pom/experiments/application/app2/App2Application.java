/*
 * Copyright (c) 2018. Yuriy Stul
 */

package com.stulsoft.pom.experiments.application.app2;
import com.stulsoft.pom.experiments.common.util.Utils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MutablePropertySources;

import javax.inject.Inject;
/**
 * @author Yuriy Stul
 */
@SpringBootApplication
public class App2Application implements CommandLineRunner, InitializingBean {
    @Inject
    private ConfigurableEnvironment env;

    @Inject
    private ApplicationContext appContext;

    @Override
    public void afterPropertiesSet() {


    }

    /**
     * Callback used to run the bean.
     *
     * @param args incoming main method arguments
     * @throws Exception on error
     */
    @Override
    public void run(String... args) throws Exception {
        System.out.println("==>run App2");
        System.out.println("Properties:");
        MutablePropertySources mps = env.getPropertySources();
        mps.forEach(ps -> {
            System.out.printf("Property source name: %s%n", ps.getName());
        });
        System.out.printf("env.getProperty(\"test.name\"): %s%n", env.getProperty("test.name"));
        System.out.println("<==run  App2");
    }
}