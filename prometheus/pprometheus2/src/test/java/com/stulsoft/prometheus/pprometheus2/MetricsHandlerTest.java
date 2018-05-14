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
import io.prometheus.client.Gauge;
import io.prometheus.client.Histogram;
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
	private static Counter testCounter3;
	private static Gauge testGauge1;
	private static Histogram testHistogram1;
	private static Histogram testHistogram2;

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
		testCounter1 = Counter.build("test_counter_1", "Test counter #1").register(registry);
		testCounter2 = Counter.build("test_counter_2", "Test counter #2").register(registry);
		testCounter3 = Counter.build("test_counter_3", "Test counter #3").labelNames("label").register(registry);
		testGauge1 = Gauge.build("test_gauge_1", "Test gauge 1").register(registry);
		testHistogram1 = Histogram.build("test_histogram_1", "Test histogram #1").register(registry);
		testHistogram2 = Histogram
				.build("test_histogram_2", "Test histogram #2")
				.buckets(0.5, 1.0, 2.0, 3.0, 4.0)
				.register(registry);
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

	@Test
	public void testForGause1() {
		System.out.println("==>testForGause1");

		testGauge1.inc();
		showMetrics();

		testCounter3.labels("the_test_label").inc(15.0);
		testCounter3.labels("the_test_label_2").inc(30.0);

		testGauge1.dec();
		showMetrics();
	}

	@Test
	public void testForHistogram1() {
		System.out.println("==>testForHistogram1");
		System.out.println("testForHistogram1 -> 1");
		Histogram.Timer timer = testHistogram1.startTimer();
		try {
			Thread.sleep(500);
		} catch (InterruptedException ignore) {
		} finally {
			timer.observeDuration();
			showMetrics();
		}

		System.out.println("testForHistogram1 -> 2");
		timer = testHistogram1.startTimer();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException ignore) {
		} finally {
			timer.observeDuration();
			showMetrics();
		}
	}

	@Test
	public void testForHistogram2() {
		System.out.println("==>testForHistogram2");
		System.out.println("testForHistogram2 -> 1");
		Histogram.Timer timer = testHistogram2.startTimer();
		try {
			Thread.sleep(500);
		} catch (InterruptedException ignore) {
		} finally {
			timer.observeDuration();
			showMetrics();
		}

		System.out.println("testForHistogram2 -> 2");
		timer = testHistogram2.startTimer();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException ignore) {
		} finally {
			timer.observeDuration();
			showMetrics();
		}
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
