/*
   Created by Yuriy Stul 2018
*/
package com.stulsoft.pjunit4;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.*;

/**
 * New way for java unit testing
 */
@RunWith(JUnit4.class)
public class SomeClassTest1 {

    @Test
    public void fooTest() {
        assertEquals(3, new SomeClass().foo("123"));
    }
}