/*
 * Copyright (c) 2022 StulSoft
 */

package com.stulsoft.poc.pgson.custom;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.FileReader;
import java.util.Map;

public class NestedEx1 {
    public static void main(String[] args) {
        System.out.println("==>main");
        test1();
    }

    static void test1() {
        System.out.println("==>test1");
        try {
            var reader = new FileReader("app/src/main/resources/file.json");
            JsonObject jsonObject = (JsonObject) JsonParser.parseReader(reader);
            reader.close();
            for (Map.Entry<String, JsonElement> element : jsonObject.entrySet()) {
                System.out.printf("element.key=%s, isJsonObject=%b, isJsonPrimitive=%b, isJsonArray=%b%n",
                        element.getKey(),
                        element.getValue().isJsonObject(),
                        element.getValue().isJsonPrimitive(),
                        element.getValue().isJsonArray());
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
