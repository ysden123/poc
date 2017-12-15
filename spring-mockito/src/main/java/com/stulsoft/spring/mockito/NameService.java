/*
 * Copyright (c) 2017. Yuriy Stul
 */

package com.stulsoft.spring.mockito;

import org.springframework.stereotype.Service;

/**
 * @author Yuriy Stul
 * @see <a href="http://www.baeldung.com/injecting-mocks-in-spring">Injecting Mockito Mocks into Spring Beans</a>
 */
@Service
public class NameService {
    public String getUserName(String id) {
        return "Real user name";
    }
}