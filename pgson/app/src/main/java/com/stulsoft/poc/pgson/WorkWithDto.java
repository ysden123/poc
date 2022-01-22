package com.stulsoft.poc.pgson;

import com.google.gson.Gson;

public class WorkWithDto {
    public static void main(String[] args) {
        System.out.println("WorkWithDto::main");

        test1();
    }

    private static void test1(){
        System.out.println("WorkWithDto::test1");

        var dto = new Dto();
        dto.setId(1);
        dto.setName("name1");
        System.out.printf("original dto: %s%n", dto);

        // Convert to JSON
        var gson = new Gson();
        var jsonAsString = gson.toJson(dto);
        System.out.printf("dto as JSON: %s%n", jsonAsString);

        var receivedDto = gson.fromJson(jsonAsString, Dto.class);
        System.out.printf("received dto: %s%n", dto);
    }
}
