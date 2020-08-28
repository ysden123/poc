/*
 * Copyright (c) 2018. Yuriy Stul
 */

package com.stulsoft.poc.pjunit5.spring.boot;

import org.junit.jupiter.api.Test;

/**
 * @author Yuriy Stul
 */
class SomeServiceTest {

    @Test
    void doServiceWork() {
        new SomeService().doServiceWork();
    }
}