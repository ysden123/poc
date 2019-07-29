/*
 * Copyright (c) 2019. Yuriy Stul
 */

package com.webpals.test1;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestStreamHandler;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author Yuriy Stul
 */
public class Main implements RequestStreamHandler {
    public void handleRequest(InputStream inputStream, OutputStream outputStream, Context context) throws IOException {
        outputStream.write("Greeting".getBytes("UTF-8"));
    }
}
