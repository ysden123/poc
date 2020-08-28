/*
 * Copyright (c) 2018. Yuriy Stul
 */

package com.stulsoft.poc.pjunit5.spring.boot;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * @author Yuriy Stul
 */
@ExtendWith(SpringExtension.class)
@TestPropertySource(properties = "app.scheduling.enable=false")
@SpringBootTest
@ComponentScan(basePackages = "com.stulsoft.poc.pjunit5.spring.boot")
class SomeComponentTest {
    @Autowired
    private SomeComponent someComponent;

    @Test
    void work() {
        someComponent.work();
    }
}