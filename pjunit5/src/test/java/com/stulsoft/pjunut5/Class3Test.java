package com.stulsoft.pjunut5;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * @author Yuriy Stul.
 */
class Class3Test {

    @Test
    @DisplayName("getP should return value of p")
    void getP() {
        Class3 c = new Class3();
        assertNull(c.getP());
        String p = "ppp";
        c.setP(p);
        assertEquals(p, c.getP());
    }

    @Test
    @DisplayName("setP should update value of p")
    void setP() {
        Class3 c = new Class3();
        assertNull(c.getP());
        String p = "ppp";
        c.setP(p);
        assertEquals(p, c.getP());
    }
}