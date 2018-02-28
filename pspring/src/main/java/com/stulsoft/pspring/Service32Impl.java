/*
 * Copyright (c) 2017, Yuriy Stul. All rights reserved
 */
package com.stulsoft.pspring;

import org.springframework.stereotype.Component;

import java.util.Random;

/**
 * @author Yuriy Stul.
 */
@Component
public class Service32Impl implements IService3 {
    private Random random = new Random();

    @Override
    public int getNextInt() {
        return random.nextInt(100);
    }
}
