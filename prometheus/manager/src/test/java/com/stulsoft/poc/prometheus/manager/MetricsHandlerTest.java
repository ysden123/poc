/*
 * Created by Yuriy Stul 17 May 2018
 */
package com.stulsoft.poc.prometheus.manager;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.URL;
import java.util.Scanner;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;

/**
 * Unit tests for MetricsHandler class.
 *
 * @author Yuriy Stul
 */
public class MetricsHandlerTest {
    private static Vertx vertx;
    private static Integer port;

    @BeforeClass
    public static void setUp() throws IOException {
        vertx = Vertx.vertx();
        final Vertx vertx = Vertx.vertx();
        final Router router = Router.router(vertx);
        router.route("/metrics").handler(MetricsManager.getInstance().getMetricsHandler());

        ServerSocket socket = new ServerSocket(0);
        port = socket.getLocalPort();
        socket.close();
        vertx.createHttpServer().requestHandler(router::accept).listen(port);

        MetricsManager.getInstance().addCounter("testService", "testCounter1", "A test counter 1");
        MetricsManager.getInstance().addCounter("testService", "testCounter2", "A test counter 2");
        MetricsManager.getInstance().addCounter("testService", "testCounter3", "A test counter 2");

        // Pause - give time for HttpServer to up...
        try {
            Thread.sleep(1000);
        } catch (Exception ignore) {
        }
    }

    @AfterClass
    public static void tearDown() {
        vertx.close();
    }

    @Test
    public void metricsRequest_shouldReturnMetrics() {
        String out = makeRequest("/metrics");
        if (out == null) fail("No output received!");
        assertTrue(out.contains("testService_testCounter1 0.0"));
        assertTrue(out.contains("testService_testCounter2 0.0"));
        assertTrue(out.contains("testService_testCounter3 0.0"));
    }

    @Test
    public void metricsRequest_shouldAllowFilteringMetrics() {
        String filter = String.format("name[]=%s_testService_testCounter2&name[]=%s_testService_testCounter3",
                MetricsManager.METRICS_NAME_PREFIX, MetricsManager.METRICS_NAME_PREFIX);
        String out = makeRequest("/metrics?" + filter);
        if (out == null) fail("No output received!");
        assertFalse(out.contains("testService_testCounter1 0.0"));
        assertTrue(out.contains("testService_testCounter2 0.0"));
        assertTrue(out.contains("testService_testCounter3 0.0"));
    }

    @Test
    public void testDefaulMetrics() {
        MetricsManager.getInstance().initDefaultMetrics();

        String out = makeRequest("/metrics");
        if (out == null) fail("No output received!");
//		System.out.println(out);
        assertTrue(out.contains("jvm_"));
    }

    private String makeRequest(String url) {
        try (Scanner scanner = new Scanner(new URL("http://localhost:" + port + url).openStream(), "UTF-8")
                .useDelimiter("\\A")) {
            String out = scanner.next();
            if (out == null) fail("No output received!");
//			System.out.printf("makeRequest: out=%s%n", out);
            return out;
        } catch (Exception ignore) {
            return null;
        }
    }
}
