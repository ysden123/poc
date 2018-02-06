/*
   Created by Yuriy Stul 2018
*/
package com.stulsoft.pspring.pinject;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Configuration1 {
    @Bean
    public Service1 service1() {
        return new Service1Impl();
    }
}
