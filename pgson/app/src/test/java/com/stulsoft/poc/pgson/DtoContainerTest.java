/*
 * Copyright (c) 2022 StulSoft
 */

package com.stulsoft.poc.pgson;
import com.google.gson.Gson;
import com.stulsoft.poc.pgson.dto.Dto;
import com.stulsoft.poc.pgson.dto.DtoContainer;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DtoContainerTest {
    @Test
    public void convertToJsonString(){
        var dto = new Dto();
        dto.setId(1);
        dto.setName("name1");
        var dtoContainer = new DtoContainer();
        dtoContainer.setName("container 1");
        dtoContainer.setDtos(Arrays.asList(dto));
        var gson = new Gson();
        var jsonAsString = gson.toJson(dtoContainer);
        var expectedJson = """
                {"name":"container 1","dtos":[{"id":1,"name":"name1"}]}""";
        assertEquals(expectedJson, jsonAsString);
    }

    @Test
    public void convertFromString(){
        var dto = new Dto();
        dto.setId(1);
        dto.setName("name1");
        var dtoContainer = new DtoContainer();
        dtoContainer.setName("container 1");
        dtoContainer.setDtos(Arrays.asList(dto));
        var gson = new Gson();
        var jsonAsString = gson.toJson(dtoContainer);
        var convertedDto = gson.fromJson(jsonAsString, DtoContainer.class);
        assertEquals(dtoContainer, convertedDto);
    }
}
