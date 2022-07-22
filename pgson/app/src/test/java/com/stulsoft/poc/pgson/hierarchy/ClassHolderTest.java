package com.stulsoft.poc.pgson.hierarchy;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Yuriy Stul
 **/
class ClassHolderTest {

    @Test
    void test1() {
        ClassHolder classHolder1 = new ClassHolder(String.class);
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(String.class, new StringAdapter())
                .create();
        String json = gson.toJson(classHolder1);
        System.out.println(classHolder1);
//        ClassHolder classHolder2 = gson.fromJson(json, ClassHolder.class);
//        assertEquals(classHolder1.toString(), classHolder2.toString());
    }
}