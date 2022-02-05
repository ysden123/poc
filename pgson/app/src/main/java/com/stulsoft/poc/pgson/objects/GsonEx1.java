/*
 * Copyright (c) 2022 StulSoft
 */

package com.stulsoft.poc.pgson.objects;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class GsonEx1 {
    public static void main(String[] args) {
        System.out.println("==>main");
        test1();
        test2();
    }

    private static void test1(){
        System.out.println("==>test1");
        JsonObject jsonObject=new JsonObject();
        jsonObject.addProperty("name", "the name");
        jsonObject.addProperty("type", "TYPE1");
        System.out.println(jsonObject);
        Gson gson = new Gson();
        TestObject3 testObject = gson.fromJson(jsonObject.toString(), TestObject3.class);
        System.out.println(testObject);
    }

    private static void test2(){
        System.out.println("==>test2");
        JsonObject jsonObject=new JsonObject();
        jsonObject.addProperty("name", "the name");
        jsonObject.addProperty("type", "TYPE_ERROR");
        System.out.println(jsonObject);
        Gson gson = new Gson();
        TestObject3 testObject = gson.fromJson(jsonObject.toString(), TestObject3.class);
        System.out.println(testObject);
    }
}
