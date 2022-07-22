package com.stulsoft.poc.pgson.hierarchy;

/**
 * @author Yuriy Stul
 **/
public class ClassHolder {
    private Class clazz;
    ClassHolder(){}

    public ClassHolder(Class clazz) {
        this.clazz = clazz;
    }

    public Class getClazz() {
        return clazz;
    }

    @Override
    public String toString() {
        return "ClassHolder{" +
                "clazz=" + clazz +
                '}';
    }
}
