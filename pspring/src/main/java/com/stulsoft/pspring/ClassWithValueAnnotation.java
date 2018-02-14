/*
   Created by Yuriy Stul 2018
*/
package com.stulsoft.pspring;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Playing Spring annotation <i>{@code @Value}</i>
 *
 * @author Yuriy Stul
 * @since 2/14/2018
 */
@Component
public class ClassWithValueAnnotation {
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
