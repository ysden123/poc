package com.stulsoft.poc.prometheus.pprometheus3;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.URL;
import java.util.Scanner;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.fail;

import io.prometheus.client.CollectorRegistry;
import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;

/**
 * Playing with default metrics
 * 
 * @author Yuriy Stul
 * 
 *         Created 15 May 2018
 *
 */
public class Test4 {
	private static Vertx vertx;
	private static Integer port;
	private static CollectorRegistry registry;

	@BeforeClass
	public static void setUp() throws IOException {
		vertx = Vertx.vertx();
		final Vertx vertx = Vertx.vertx();
		final Router router = Router.router(vertx);

		registry = new CollectorRegistry();
		router.route("/metrics").handler(new MetricsHandler(registry));
		
		InitDefaultExports.initialize(registry);

		ServerSocket socket = new ServerSocket(0);
		port = socket.getLocalPort();
		System.out.println("port=" + port);
		socket.close();
		vertx
				.createHttpServer()
				.requestHandler(router::accept)
				.listen(port);
		
		try {
			Thread.sleep(100);
		} catch (InterruptedException ignore) {
		}
	}

	@AfterClass
	public static void tearDown() {
		vertx.close();
	}

	@Test
	public void testdefault() {
		System.out.println("==>testdefault");
		showMetrics();
		System.out.println("<==testdefault");

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
		} catch (Exception e) {
			String msg = "Failed getting metrics. " + e.getMessage();
			System.err.println(msg);
			fail(msg);
			return null;
		}
	}

}
