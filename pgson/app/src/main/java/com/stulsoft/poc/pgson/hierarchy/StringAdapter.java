package com.stulsoft.poc.pgson.hierarchy;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

/**
 * @author Yuriy Stul
 **/
public class StringAdapter extends TypeAdapter<String> {
    @Override
    public void write(JsonWriter out, String value) throws IOException {
        out.beginObject();
        out.value(value);
        out.endObject();
    }

    @Override
    public String read(JsonReader in) throws IOException {
        return null;
    }
}
