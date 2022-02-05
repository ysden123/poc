/*
 * Copyright (c) 2022 StulSoft
 */

package com.stulsoft.poc.pgson.objects;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

public class GsonEx {
    public static void main(String[] args) {
        System.out.println("==>main");
        test1();
        test2();
    }

    private static void test1(){
        System.out.println("==>test1");
        JsonObject jsonObject=new JsonObject();
        jsonObject.addProperty("name", "the name");
        jsonObject.addProperty("age", 5);
        System.out.println(jsonObject);
        Gson gson = new Gson();
        TestObject testObject = gson.fromJson(jsonObject.toString(), TestObject.class);
        System.out.println(testObject);
    }

    private static void test2(){
        System.out.println("==>test2");
        try {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("objectName", "TestObject2");
            jsonObject.addProperty("name", "the name");
            jsonObject.addProperty("age", 5);
            jsonObject.addProperty("date", "2022-02-01 00:00:00");
            System.out.println(jsonObject);
            Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
            TestObject2 testObject = gson.fromJson(jsonObject.toString(), TestObject2.class);
            System.out.println(testObject);
        }catch(Exception exception){
            exception.printStackTrace();
        }
    }
}
