/*
 * Copyright (c) 2021. Yuriy Stul
 */

package com.stulsoft.poc.json.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author Yuriy Stul
 */
public class RequestTest {
    public static void main(String[] args) {
        System.out.println("==>main");

        try {
            test1();
            test2();
        }catch(Exception exception){
            exception.printStackTrace();
        }
    }

    private static void test1() throws JsonProcessingException {
        System.out.println("==>test1");
        var jsonText = """
                {
                    "filters": [
                        {"name": "n1", "operation": "eq", "value": 123},
                        {"name": "n2", "operation": "gte", "value": "abc"}
                    ]
                }
                """;
        ObjectMapper objectMapper = new ObjectMapper();

        var request = objectMapper.readValue(jsonText, Request.class);
        assert (request.filterList().isPresent());

        System.out.println(request);
    }

    private static void test2() throws JsonProcessingException {
        System.out.println("==>test2");
        var jsonText = """
                {
                }
                """;
        ObjectMapper objectMapper = new ObjectMapper();

        var request = objectMapper.readValue(jsonText, Request.class);
        assert (request.filterList().isEmpty());

        System.out.println(request);
    }
}
