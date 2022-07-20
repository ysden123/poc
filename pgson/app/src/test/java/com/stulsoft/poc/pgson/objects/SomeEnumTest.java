package com.stulsoft.poc.pgson.objects;

import com.google.gson.Gson;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Yuriy Stul
 **/
class SomeEnumTest {

    @Test
    void test1(){
        Gson gson =new Gson();

        SomeEnum someEnum = SomeEnum.ENUM1;
        SomeEnum2 someEnum2 = SomeEnum2.VAL1;
        ContainerForSomeEnum containerForSomeEnum1 = new ContainerForSomeEnum(someEnum, someEnum2);

        String json = gson.toJson(containerForSomeEnum1);
        System.out.println(json);

        ContainerForSomeEnum containerForSomeEnum2 = gson.fromJson(json, ContainerForSomeEnum.class);
        System.out.println(containerForSomeEnum2);
        assertEquals(containerForSomeEnum1.toString(), containerForSomeEnum2.toString());
    }

}