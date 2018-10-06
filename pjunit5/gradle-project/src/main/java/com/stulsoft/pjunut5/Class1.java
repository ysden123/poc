/*
 * Copyright (c) 2017. Yuriy Stul
 */

package com.stulsoft.pjunut5;

/**
 * @author Yuriy Stul
 */
public class Class1 {
    private String p1;

    public String getP1() {
        return p1;
    }

    public void setP1(String p1) {
        this.p1 = p1;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Class1)) return false;

        Class1 class1 = (Class1) o;

        return p1 != null ? p1.equals(class1.p1) : class1.p1 == null;
    }

    @Override
    public int hashCode() {
        return p1 != null ? p1.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Class1{" +
                "p1='" + p1 + '\'' +
                '}';
    }
}
