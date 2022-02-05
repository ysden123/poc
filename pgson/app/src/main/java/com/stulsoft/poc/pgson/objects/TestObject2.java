/*
 * Copyright (c) 2022 StulSoft
 */

package com.stulsoft.poc.pgson.objects;

import java.util.Date;

public class TestObject2 extends BaseObject {
    private String name;
    private Integer age;
    private Date date;

    @Override
    public String toString() {
        return "TestObject2{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", date=" + date +
                '}';
    }
}
