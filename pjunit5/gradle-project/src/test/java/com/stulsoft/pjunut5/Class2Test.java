/*
 * Copyright (c) 2017. Yuriy Stul
 */

package com.stulsoft.pjunut5;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author Yuriy Stul
 */
@DisplayName("Exception handling")
class Class2Test {
    @Test
    void setP() {
        Class2 c2 = new Class2();
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            c2.setP(null);
        });
        assertEquals("p should be defined.", exception.getMessage());

        exception = assertThrows(IllegalArgumentException.class, () -> {
            c2.setP("");
        });
        assertEquals("p should be defined.", exception.getMessage());
    }

}