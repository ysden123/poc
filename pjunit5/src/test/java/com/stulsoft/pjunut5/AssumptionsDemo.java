/*
 * Copyright (c) 2017. Yuriy Stul
 */

package com.stulsoft.pjunut5;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assumptions.assumeTrue;
import static org.junit.jupiter.api.Assumptions.assumingThat;

/**
 * @author Yuriy Stul
 */
public class AssumptionsDemo {
    @Test
    void testOnlyOnTrue() {
        assumeTrue(true);
        // remainder of test
        assertTrue(true);
    }

    @Test
    void testOnlyOnTrue2() {
        assumeTrue(false, () -> "Aborting test");
        // remainder of test
        assertTrue(false);
    }

    @Test
    void testAssumeThat() {
        assumingThat(true, () -> {
            System.out.println("Some special test");
            assertTrue(true);
        });

        System.out.println("Continue test...");
    }

    @Test
    void testAssumeThat2() {
        assumingThat(false, () -> {
            System.out.println("Some special test 2");
            assertTrue(true);
        });

        System.out.println("Continue test 2 ...");
    }
}
