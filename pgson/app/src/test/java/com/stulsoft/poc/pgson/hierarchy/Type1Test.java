package com.stulsoft.poc.pgson.hierarchy;

import com.google.gson.Gson;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author Yuriy Stul
 **/
class Type1Test {
    class Container {
        private ClassHolder classHolder;

        Container() {

        }

        public Container(ClassHolder classHolder) {
            this.classHolder = classHolder;
        }

        @Override
        public String toString() {
            return "Container{" +
                    "classHolder=" + classHolder +
                    '}';
        }
    }

    @Test
    void test1() {
        Container container1 = new Container(new Type1("name1"));
        Gson gson = new Gson();
        String json = gson.toJson(container1);
        Container container2 = gson.fromJson(json, Container.class);
        assertEquals(container1.toString(), container2.toString());
    }
}