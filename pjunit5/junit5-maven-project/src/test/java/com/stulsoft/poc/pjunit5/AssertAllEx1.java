/*
 * Copyright (c) 2018. Yuriy Stul
 */

package com.stulsoft.poc.pjunit5;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author Yuriy Stul
 */
public class AssertAllEx1 {

    /**
     * Unit test fails on 1st error and does not continue else tests.
     */
    @Test
    @DisplayName("Without assertAll")
    void withoutAssertAll() {
        assertEquals("111", "222");  // Error
        assertEquals("333", "444");  // Error
        assertEquals("555", "555");  // Not error
    }

    /**
     * Unit test checks all tests.
     */
    @Test
    @DisplayName("With assertAll")
    void withAssertAll() {
        assertAll("Many asserts",
                () -> assertEquals("111", "222"),   // error
                () -> assertEquals("333", "444"),   // error
                () -> assertEquals("555", "555")    // not error
        );
    }
}
