/*
 * Copyright (c) 2017. Yuriy Stul
 */

package com.stulsoft.spring.mockito;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Yuriy Stul
 * @see <a href="http://www.baeldung.com/injecting-mocks-in-spring">Injecting Mockito Mocks into Spring Beans</a>
 */
@Service
public class UserService {

    private NameService nameService;

    @Autowired
    public UserService(NameService nameService) {
        this.nameService = nameService;
    }

    public String getUserName(String id) {
        return nameService.getUserName(id);
    }
}