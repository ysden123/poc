/*
 * Copyright (c) 2022 StulSoft
 */

package com.stulsoft.poc.pgson.adapter;

public class Student {
    private String name;
    private Integer age;

    private Sex sex;

    private Student() {

    }

    public Student(String name, Integer age, Sex sex) {
        this.name = name;
        this.age = age;
        this.sex = sex;
    }

    public String getName() {
        return name;
    }

    public Integer getAge() {
        return age;
    }

    public Sex getSex() {
        return sex;
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", sex=" + sex +
                '}';
    }
}
