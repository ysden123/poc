package com.stulsoft.poc.pgson;

import com.google.gson.Gson;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WorkWithDtoTest {
    @Test
    public void convertToJsonString(){
        var dto = new Dto();
        dto.setId(1);
        dto.setName("name1");
        var gson = new Gson();
        var jsonAsString = gson.toJson(dto);
        assertEquals("{\"id\":1,\"name\":\"name1\"}", jsonAsString);
    }

    @Test
    public void convertFromString(){
        var dto = new Dto();
        dto.setId(1);
        dto.setName("name1");
        var gson = new Gson();
        var jsonAsString = gson.toJson(dto);
        var convertedDto = gson.fromJson(jsonAsString, Dto.class);
        assertEquals(dto, convertedDto);
    }
}
