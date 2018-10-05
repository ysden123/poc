/*
 * Copyright (c) 2017. Yuriy Stul
 */

package com.stulsoft.pjunut5;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assumptions.assumeTrue;
import static org.junit.jupiter.api.Assumptions.assumingThat;

/**
 * @author Yuriy Stul
 */
class AssumptionsDemo {
    @Test
    void testOnlyOnTrue() {
        System.out.println("==>testOnlyOnTrue");
        System.out.println("Check assumption (true)");
        assumeTrue(true);
        // remainder of test
        System.out.println("Continue test");
        assertTrue(true);
        System.out.println("<==testOnlyOnTrue");
    }

    @Test
    @DisplayName("Should work per dev env")
    void testOnlyOnTrue2() {
        System.out.println("==>testOnlyOnTrue2");
        System.out.println("Check assumption (false)");
        assumeTrue(false, () -> "Aborting test");
        // remainder of test
        System.out.println("Continue test");
        assertTrue(false);
        System.out.println("<==testOnlyOnTrue2");
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
