package com.stulsoft.poc.pgson;


import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BuildJsonTest {

    @Test
    public void buildJson1() {
        var json = new JsonObject();
        var id = new JsonPrimitive(1);
        json.add("id", id);
        json.add("name", new JsonPrimitive("name 123"));
        var expectedJson = """
                {"id":1,"name":"name 123"}""";
        assertEquals(expectedJson, json.toString());
    }

    @Test
    public void buildJson2() {
        var json = new JsonObject();
        var array = new JsonArray();
        array.add("line 1");
        array.add("line 2");
        array.add("line 3");

        json.add("array", array);
        var expectedJson = """
                {"array":["line 1","line 2","line 3"]}""";
        assertEquals(expectedJson, json.toString());
    }

    @Test
    public void buildJson3() {
        var json = new JsonObject();
        var array = new JsonArray();
        array.add("line 1");
        array.add(new JsonPrimitive("line 2"));
        array.add("line 3");

        json.add("array", array);
        var expectedJson = """
                {"array":["line 1","line 2","line 3"]}""";
        assertEquals(expectedJson, json.toString());
    }
}
