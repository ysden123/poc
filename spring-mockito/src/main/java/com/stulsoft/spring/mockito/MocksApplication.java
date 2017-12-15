/*
 * Copyright (c) 2017. Yuriy Stul
 */

package com.stulsoft.spring.mockito;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Yuriy Stul
 * @see <a href="http://www.baeldung.com/injecting-mocks-in-spring">Injecting Mockito Mocks into Spring Beans</a>
 */
@SpringBootApplication
public class MocksApplication {
    public static void main(String[] args) {
        SpringApplication.run(MocksApplication.class, args);
    }
}
