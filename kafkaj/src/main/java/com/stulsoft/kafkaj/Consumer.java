package com.stulsoft.kafkaj;

import java.util.concurrent.Future;

/**
 * @author Yuriy Stul.
 */
public interface Consumer {
    Future<Void> start();
    Future<Void> stop();
}
