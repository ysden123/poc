/*
 * Copyright (c) 2019. Yuriy Stul
 */

package com.stulsoft.poc.json;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author Yuriy Stul
 */
public class Main {

    public static void main(String[] args) {
        System.out.println("==>main");
        var j = new JSONObject().put("p1", 123).put("p2", "text");
        System.out.println(j.toString());

        var p1 = j.getInt("p1");
        var p2 = j.getString("p2");

        try{
            var p3 = j.get("p3");
            System.out.printf("p3 = %s%n", p3);
        }catch(JSONException ex){
            System.out.printf("Exception: %s%n", ex.getMessage());
        }
        System.out.printf("p1 = %d, p2 = %s%n", p1, p2);

        var j1 = new JSONObject("{\"p1\":456,\"p2\":\"ttttt\"}");
        System.out.println(j1.toString());
        System.out.println("<==main");
    }
}
