/*
 * Copyright (c) 2022 StulSoft
 */

package com.stulsoft.poc.pgson.generic;

public class GenericClass<T> {
    private T t;

    private GenericClass(){

    }

    public GenericClass(T t) {
        this.t = t;
    }

    public T getT() {
        return t;
    }

    @Override
    public String toString() {
        return "GenericClass{" +
                "t=" + t +
                '}';
    }
}
