/*
 * Copyright (c) 2017. Yuriy Stul
 */

package com.stulsoft.pjunut5;

/**
 * @author Yuriy Stul
 */
public class Class3 {
    private String p;

    public String getP() {
        return p;
    }

    public void setP(String p) {
        if (p == null || p.isEmpty()) throw new IllegalArgumentException("p should be defined.");
        this.p = p;
    }
}
