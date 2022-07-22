/*
 * Copyright (c) 2022 StulSoft
 */

package com.stulsoft.poc.pgson.adapter;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

public class StudentAdapter extends TypeAdapter<Student> {
    /**
     * Writes one JSON value (an array, object, string, number, boolean or null)
     * for {@code value}.
     *
     * @param value the Java object to write. May be null.
     */
    @Override
    public void write(JsonWriter out, Student value) throws IOException {
        out.beginObject();
        out.name("name");
        out.value(value.getName());
        out.name("age");
        out.value(value.getAge());
        out.name("sex");
        out.value(value.getSex().toString());
        out.endObject();
    }

    /**
     * Reads one JSON value (an array, object, string, number, boolean or null)
     * and converts it to a Java object. Returns the converted object.
     *
     * @return the converted Java object. May be null.
     */
    @Override
    public Student read(JsonReader in) throws IOException {
        String name = null;
        Integer age = null;
        Sex sex = null;
        in.beginObject();
        String fieldName = null;
        while (in.hasNext()) {
            JsonToken jsonToken = in.peek();
            if (JsonToken.NAME.equals(jsonToken)) {
                fieldName = in.nextName();
            }
            if ("name".equals(fieldName)) {
                in.peek();
                name = in.nextString();
            }
            if ("age".equals(fieldName)) {
                in.peek();
                age = in.nextInt();
            }
            if ("sex".equals(fieldName)) {
                in.peek();
                sex = Sex.valueOf(in.nextString());
            }
        }
        in.endObject();
        return new Student(name, age, sex);
    }
}
