/*
 * Copyright (c) 2017. Yuriy Stul
 */

package com.stulsoft.pjunut5;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Yuriy Stul
 */
@DisplayName("Testing Class1 class")
class Class1Test2 {
    @DisplayName("Testing getP1 method")
    @Test
    void getP1() {
        Class1 c1 = new Class1();
        assertNull(c1.getP1());

        c1.setP1("p1");
        assertEquals("p1", c1.getP1());
    }

    @Test
    void setP1() {
        Class1 c1 = new Class1();

        c1.setP1("p1");
        assertEquals("p1", c1.getP1());
    }

    @Test
    void equalsTest() {
        Class1 c11 = new Class1();
        Class1 c12 = new Class1();
        assertEquals(c11, c12);

        c11.setP1("p1");
        c12.setP1("p1");
        assertEquals(c11, c12);

        assertNotSame(c11, c12);
        @SuppressWarnings("UnnecessaryLocalVariable") Class1 c13 = c11;
        assertSame(c11, c13);
    }

    @Test
    void hashCodeTest() {
        Class1 c11 = new Class1();
        Class1 c12 = new Class1();
        assertEquals(c11.hashCode(), c12.hashCode());

        c11.setP1("p1");
        c12.setP1("p1");
        assertEquals(c11.hashCode(), c12.hashCode());
    }

    @Test
    void toStringTest() {
        Class1 c1 = new Class1();
        c1.setP1("p1");
        assertTrue(!c1.toString().isEmpty());
    }

}