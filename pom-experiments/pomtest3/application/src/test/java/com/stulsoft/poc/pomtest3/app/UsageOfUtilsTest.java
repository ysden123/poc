/*
 * Copyright (c) 2018. Yuriy Stul
 */

package com.stulsoft.poc.pomtest3.app;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Yuriy Stul
 */
public class UsageOfUtilsTest {

    @Test
    public void callFoo() {
        assertEquals("Some foo text", UsageOfUtils.callFoo());
    }
}