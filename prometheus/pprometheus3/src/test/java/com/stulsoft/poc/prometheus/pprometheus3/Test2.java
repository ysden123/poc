package com.stulsoft.poc.prometheus.pprometheus3;

import static org.junit.Assert.fail;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.URL;
import java.util.Scanner;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import io.prometheus.client.CollectorRegistry;
import io.prometheus.client.Counter;
import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;

/**
 * @author Yuriy Stul
 * @since 15 May 2018
 *
 */
public class Test2 {
	private static Vertx vertx;
	private static Integer port;
	private static CollectorRegistry registry;
	private static Counter testCounter;

	@BeforeClass
	public static void setUp() throws IOException {
		vertx = Vertx.vertx();
		final Vertx vertx = Vertx.vertx();
		final Router router = Router.router(vertx);

		registry = new CollectorRegistry();
		router.route("/metrics").handler(new MetricsHandler(registry));

		ServerSocket socket = new ServerSocket(0);
		port = socket.getLocalPort();
		System.out.println("port=" + port);
		socket.close();
		vertx.createHttpServer().requestHandler(router::accept).listen(port);
		testCounter = Counter
				.build("test_counter_1", "Test counter #1")
				.labelNames("method", "type")
				.register(registry);
	}

	@AfterClass
	public static void tearDown() {
		vertx.close();
	}

	@Test
	public void testForCounter() {
		System.out.println("==>testForCounter");

		testCounter.labels("method 1", "type 1").inc();
		testCounter.labels("method 2","type 2").inc(2);
		testCounter.labels("method 2","type 3").inc(3);
		try {
			showMetrics();
		} catch (Exception e) {
			fail(e.getMessage());
		}
		System.out.println("<==testForCounter");
	}

	private void showMetrics() {
		try {
			System.out.println(makeRequest("/metrics"));
		} catch (Exception e) {
			System.err.println("failed getting metrics. " + e.getMessage());
		}
	}

	private String makeRequest(String url) throws IOException {
		try (Scanner scanner = new Scanner(new URL("http://localhost:" + port + url).openStream(), "UTF-8")
				.useDelimiter("\\A")) {
			String out = scanner.next();
			return out;
		} catch (Exception ignore) {
			return null;
		}
	}
}
