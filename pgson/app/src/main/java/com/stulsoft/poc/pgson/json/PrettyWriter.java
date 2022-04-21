/*
 * Copyright (c) 2022 StulSoft
 */

package com.stulsoft.poc.pgson.json;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.FileReader;
import java.io.FileWriter;

public class PrettyWriter {
    public static void main(String[] args) {
        System.out.println("==>PrettyWriter::main");
        parseAndWrite();
    }

    private static void parseAndWrite() {
        System.out.println("==>PrettyWriter::parseAndWrite");

        try {
            var reader = new FileReader("app/src/main/resources/fileWithStringObject.json");
            JsonObject jsonObject = (JsonObject) JsonParser.parseReader(reader);
            reader.close();

            var dataAsString=jsonObject.get("data").getAsString();
            var dataAsJson = (JsonObject)JsonParser.parseString(dataAsString);
            jsonObject.remove("data");
            jsonObject.add("data", dataAsJson);

            var gson = new GsonBuilder()
                    .setPrettyPrinting()
                    .create();
            var writer =new FileWriter("test.json");
            gson.toJson(dataAsJson, writer);
            writer.close();
        } catch (Exception exception) {
            exception.printStackTrace();
        }

    }

}
