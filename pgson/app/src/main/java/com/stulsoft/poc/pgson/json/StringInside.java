/*
 * Copyright (c) 2022 StulSoft
 */

package com.stulsoft.poc.pgson.json;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonWriter;

import java.io.FileReader;

public class StringInside {
    public static void main(String[] args) {
        System.out.println("==>StringInside::main");
        parse();
    }

    private static void parse() {
        System.out.println("==>StringInside::parse");
        try {
            var reader = new FileReader("app/src/main/resources/fileWithStringObject.json");
            JsonObject jsonObject = (JsonObject) JsonParser.parseReader(reader);
            reader.close();
            System.out.printf("%s%n", jsonObject);

            System.out.printf("data is object: %b, is primitive: %b%n",
                    jsonObject.get("data").isJsonObject(),
                    jsonObject.get("data").isJsonPrimitive()
            );

            var dataAsString=jsonObject.get("data").getAsString();
            System.out.printf("dataAsString:%s%n", dataAsString);

            var dataAsJson = (JsonObject)JsonParser.parseString(dataAsString);
            System.out.printf("dataAsJson:%s%n", dataAsJson.toString());
            var b = dataAsJson.getAsJsonPrimitive("b");
            System.out.printf("b:%s%n", b.getAsString());
        } catch (Exception exception) {
            exception.printStackTrace();
        }

    }
}
