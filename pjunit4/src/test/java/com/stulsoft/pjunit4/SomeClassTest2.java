/*
   Created by Yuriy Stul 2018
*/
package com.stulsoft.pjunit4;

import junit.framework.TestCase;

/**
 * Old way for unit testing. See {@link SomeClassTest1} with new way
 */
public class SomeClassTest2 extends TestCase {

    public void testFoo() {
        assertEquals(3, new SomeClass().foo("123"));
    }
}