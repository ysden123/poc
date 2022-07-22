/*
 * Copyright (c) 2022 StulSoft
 */

package com.stulsoft.poc.pgson.adapter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StudentTest {
    @Test
    void testWithoutAdapter() {
        Student student1 = new Student("abc", 123, Sex.male);
        Gson gson = new Gson();
        String json = gson.toJson(student1);
        Student student2 = gson.fromJson(json, Student.class);
        assertEquals(student1.toString(), student2.toString());
    }

    @Test
    void testWithAdapter() {
        Student student1 = new Student("abc", 123, Sex.female);
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Student.class, new StudentAdapter())
                .create();
        String json = gson.toJson(student1);
        Student student2 = gson.fromJson(json, Student.class);
        assertEquals(student1.toString(), student2.toString());
    }
}