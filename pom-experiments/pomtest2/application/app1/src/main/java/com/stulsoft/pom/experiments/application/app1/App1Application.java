/*
 * Copyright (c) 2018. Yuriy Stul
 */

package com.stulsoft.pom.experiments.application.app1;

import com.stulsoft.pom.experiments.common.util.Utils;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Yuriy Stul
 */
@SpringBootApplication
public class App1Application implements CommandLineRunner {

    /**
     * Callback used to run the bean.
     *
     * @param args incoming main method arguments
     * @throws Exception on error
     */
    @Override
    public void run(String... args) throws Exception {
        System.out.println("==>run");
        System.out.println(Utils.testText());
        System.out.println("<==run");
    }
}
