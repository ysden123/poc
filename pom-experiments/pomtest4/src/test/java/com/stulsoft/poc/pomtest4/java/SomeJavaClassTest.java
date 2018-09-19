/*
 * Copyright (c) 2018. Yuriy Stul
 */

package com.stulsoft.poc.pomtest4.java;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

/**
 * @author Yuriy Stul
 */
public class SomeJavaClassTest {

    @Test
    public void foo() {
        var o = new SomeJavaClass();
        assertNotNull(o.foo());
        assertFalse(o.foo().isEmpty());
    }
}