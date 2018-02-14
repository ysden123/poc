/*
   Created by Yuriy Stul 2018
*/
package com.stulsoft.pspring;

import org.springframework.beans.factory.annotation.Value;

/**
 * Playing Spring annotation <i>{@code @Value}</i>
 * <p>
 * For using without Spring initialization.
 *
 * @author Yuriy Stul
 * @since 2/14/2018
 */
public class ClassWithValueAnnotation2 {
    @Value("${test.value1:default}")
    private String value1;

    @Value("${test.value2:default}")
    private String value2;
    /*
    ERROR: value was not defined
        @Value("${test.value3}")
        private String value3;
    */
    @Value("${JAVA_HOME}")
    private String javaHome;

    public String getValue1() {
        return value1;
    }

    public String getValue2() {
        return value2;
    }

    public String getJavaHome() {
        return javaHome;
    }
}
