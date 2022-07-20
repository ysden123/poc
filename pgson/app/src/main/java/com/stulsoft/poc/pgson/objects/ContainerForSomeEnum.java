package com.stulsoft.poc.pgson.objects;

/**
 * @author Yuriy Stul
 **/
public class ContainerForSomeEnum {
    private SomeEnum someEnum;
    private SomeEnum2 someEnum2;

    private ContainerForSomeEnum() {

    }

    public ContainerForSomeEnum(SomeEnum someEnum, SomeEnum2 someEnum2) {
        this.someEnum = someEnum;
        this.someEnum2 = someEnum2;
    }

    @Override
    public String toString() {
        return "ContainerForSomeEnum{" +
                "someEnum=" + someEnum +
                "someEnum2=" + someEnum2 +
                '}';
    }
}
