/*
 * Copyright (c) 2022 StulSoft
 */

package com.stulsoft.poc.pgson.generic;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.stulsoft.poc.pgson.adapter.Sex;
import com.stulsoft.poc.pgson.adapter.Student;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Type;

class GenericClassTest {

    @Test
    void test1() {
        System.out.println("==>test1");
        GenericClass<Student> genericStudent1 = new GenericClass<>(new Student("yur", 123, Sex.female));
        System.out.println(genericStudent1);
        Gson gson = new Gson();
        String json = gson.toJson(genericStudent1);
        System.out.println(json);
        @SuppressWarnings("unchecked")
        GenericClass<Student> genericStudent2 = gson.fromJson(json, GenericClass.class);
        System.out.println(genericStudent2);
        System.out.println(genericStudent2.getT());
    }

    @Test
    void test2() {
        System.out.println("==>test2");
        GenericClass<Student> genericStudent1 = new GenericClass<>(new Student("yur", 123, Sex.female));
        System.out.println(genericStudent1);
        Type genericClassType = new TypeToken<GenericClass<Student>>() {
        }.getType();
        Gson gson = new Gson();
        String json = gson.toJson(genericStudent1, genericClassType);
        System.out.println(json);
        GenericClass<Student> genericStudent2 = gson.fromJson(json, genericClassType);
        System.out.println(genericStudent2);
        System.out.println(genericStudent2.getT());
    }
}