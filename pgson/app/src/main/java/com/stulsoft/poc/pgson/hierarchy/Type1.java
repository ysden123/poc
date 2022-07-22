package com.stulsoft.poc.pgson.hierarchy;

/**
 * @author Yuriy Stul
 **/
public class Type1 extends ClassHolder {
    private String name;

    public Type1() {}

    public Type1(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Type1{" +
                "name='" + name + '\'' +
                '}';
    }
}
