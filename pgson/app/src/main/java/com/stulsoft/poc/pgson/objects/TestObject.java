/*
 * Copyright (c) 2022 StulSoft
 */

package com.stulsoft.poc.pgson.objects;

public class TestObject {
    private String name;
    private Integer age;

    @Override
    public String toString() {
        return "TestObject{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
