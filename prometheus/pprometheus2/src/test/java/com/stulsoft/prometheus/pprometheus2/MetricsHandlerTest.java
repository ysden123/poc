package com.stulsoft.prometheus.pprometheus2;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.URL;
import java.util.Scanner;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.stulsoft.prometheus.pprometheus2.MetricsHandler;

import io.prometheus.client.CollectorRegistry;
import io.prometheus.client.Counter;
import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;

/**
 * @author Yuriy Stul
 *
 */
public class MetricsHandlerTest {
	private static Vertx vertx;
	private static Integer port;
	private static CollectorRegistry registry;
	private static Counter testCounter1;
	private static Counter testCounter2;

	@BeforeClass
	public static void setUp() throws IOException {
		vertx = Vertx.vertx();
		final Vertx vertx = Vertx.vertx();
		final Router router = Router.router(vertx);

		registry = new CollectorRegistry();
		router.route("/metrics").handler(new MetricsHandler(registry));

		ServerSocket socket = new ServerSocket(0);
		port = socket.getLocalPort();
		System.out.println("port="+port);
		socket.close();
		vertx.createHttpServer().requestHandler(router::accept).listen(port);
		testCounter1 = Counter.build("test_counter_1", "Test counter #1").register(registry);
		testCounter2 = Counter.build("test_counter_2", "Test counter #2").register(registry);
	}

	@AfterClass
	public static void tearDown() {
		vertx.close();
	}

	@Test
	public void testForCounter1() {
		System.out.println("==>testForCounter1");
		testCounter1.inc();
		try {
			String out = makeRequest("/metrics");
			System.out.printf("testForCounter1: out=%s%n", out);
			assertThat(out).contains("test_counter_1 1.0");
			assertThat(out).contains("test_counter_2 0.0");
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void testForCounter2() {
		System.out.println("==>testForCounter2");
		testCounter2.inc();
		try {
			String out = makeRequest("/metrics");
			System.out.printf("testForCounter2: out=%s%n", out);
			assertThat(out).contains("test_counter_1 1.0");
			assertThat(out).contains("test_counter_2 1.0");
		} catch (Exception e) {
			fail(e.getMessage());
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
