/*
 * Copyright (c) 2022 StulSoft
 */

package json;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.FileReader;

public class FromFile {
    public static void main(String[] args) {
        System.out.println("==>FromFile::main");

        fileToJson();
    }

    private static void fileToJson() {
        System.out.println("==>FromFile::fileToJson");
        try {
            var reader = new FileReader("app/src/main/resources/file.json");
            JsonObject jsonObject = (JsonObject) JsonParser.parseReader(reader);
            reader.close();
            System.out.printf("%s%n", jsonObject);

            System.out.printf("abc is object: %b, is primitive: %b%n",
                    jsonObject.get("abc").isJsonObject(),
                    jsonObject.get("abc").isJsonPrimitive()
            );
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
