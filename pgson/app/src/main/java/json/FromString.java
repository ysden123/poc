/*
 * Copyright (c) 2022 StulSoft
 */

package json;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class FromString {
    public static void main(String[] args) {
        System.out.println("==>FromString::main");

        stringToJson();
    }

    private static void stringToJson() {
        System.out.println("==>FromString::stringToJson");
        try {
            var jsonString = """
                    {
                        "abc":123,
                        "a": [
                            {"n":1},
                            {"n":2},
                            {"n":3}
                        ]
                    }
                    """;
            JsonObject jsonObject = (JsonObject) JsonParser.parseString(jsonString);
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
