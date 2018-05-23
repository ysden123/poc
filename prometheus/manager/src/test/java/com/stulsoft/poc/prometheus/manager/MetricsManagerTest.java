package com.stulsoft.poc.prometheus.manager;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import io.prometheus.client.Counter;
import io.prometheus.client.Gauge;

/**
 * Unit tests for MetricsManager class.
 * 
 * @author Yuriy Stul
 *
 */
public class MetricsManagerTest {

	@Test
	public void testGetInstance() {
		MetricsManager metricsManager = MetricsManager.getInstance();
		assertNotNull(metricsManager);
	}

	@Test
	public void testAddCounter() {
		Counter counter1 = MetricsManager.getInstance().addCounter("serviceName", "counterName", "description");
		assertNotNull(counter1);
		Counter counter2 = MetricsManager.getInstance().addCounter("serviceName", "counterName", "description");
		assertNotNull(counter2);
		assertSame(counter1, counter2);
	}

	@Test
	public void testAddCounter_withLables() {
		Counter counter = MetricsManager.getInstance().addCounter("serviceName", "counterNameWithLabels", "description", "label1", "label2");
		assertNotNull(counter);
		counter.labels("label1", "label2").inc();
		List<String> labels = new ArrayList<>();
		counter.collect().forEach(c -> c.samples.forEach(s -> labels.addAll(s.labelNames)));
		assertTrue(labels.contains("label1"));
		assertTrue(labels.contains("label2"));
	}

	@Test
	public void testAddCounter_failure() {
		try {
			MetricsManager.getInstance().addCounter(null, "counterName", "description");
			fail("serviceName should be validated");
		} catch (NullPointerException e) {
			assertEquals("serviceName should be defined", e.getMessage());
		}

		try {
			MetricsManager.getInstance().addCounter("serviceName", null, "description");
			fail("counterName should be validated");
		} catch (NullPointerException e) {
			assertEquals("counterName should be defined", e.getMessage());
		}

		try {
			MetricsManager.getInstance().addCounter("serviceName", "counterName", null);
			fail("description should be validated");
		} catch (NullPointerException e) {
			assertEquals("description should be defined", e.getMessage());
		}
	}

	@Test
	public void testGetCounter() {
		Counter counter1 = MetricsManager.getInstance().addCounter("serviceName", "counterName", "description");
		Counter counter2 = MetricsManager.getInstance().getCounter("serviceName", "counterName");
		assertEquals(counter1, counter2);
		assertSame(counter1, counter2);
	}
	
	@Test
	public void testGetCounter_failure() {
		try {
			MetricsManager.getInstance().getCounter("wrongServiceName", "wrongCounterName");
			fail("No exception occured");
		}catch(RuntimeException e) {
			String expectedMessage = "Counter with service name wrongServiceName and counter name wrongCounterName doesn't exist.";
			assertEquals(expectedMessage, e.getMessage());
		}
	}
	
	@Test
	public void testAddGauge() {
		Gauge gauge1 = MetricsManager.getInstance().addGauge("serviceName", "gaugeName", "description");
		assertNotNull(gauge1);
		Gauge gauge2 = MetricsManager.getInstance().addGauge("serviceName", "gaugeName", "description");
		assertNotNull(gauge2);
		assertSame(gauge1, gauge2);
	}

	@Test
	public void testAddGauge_withLables() {
		Gauge gauge = MetricsManager.getInstance().addGauge("serviceName", "gaugeNameWithLabels", "description", "label1", "label2");
		assertNotNull(gauge);
		gauge.labels("label1", "label2").inc();
		List<String> labels = new ArrayList<>();
		gauge.collect().forEach(c -> c.samples.forEach(s -> labels.addAll(s.labelNames)));
		assertTrue(labels.contains("label1"));
		assertTrue(labels.contains("label2"));
	}

	@Test
	public void testAddGauge_failure() {
		try {
			MetricsManager.getInstance().addGauge(null, "gaugeName", "description");
			fail("serviceName should be validated");
		} catch (NullPointerException e) {
			assertEquals("serviceName should be defined", e.getMessage());
		}

		try {
			MetricsManager.getInstance().addGauge("serviceName", null, "description");
			fail("counterName should be validated");
		} catch (NullPointerException e) {
			assertEquals("counterName should be defined", e.getMessage());
		}

		try {
			MetricsManager.getInstance().addGauge("serviceName", "gaugeName", null);
			fail("description should be validated");
		} catch (NullPointerException e) {
			assertEquals("description should be defined", e.getMessage());
		}
	}

	@Test
	public void testGetGauge() {
		Gauge counter1 = MetricsManager.getInstance().addGauge("serviceName", "gaugeName", "description");
		Gauge counter2 = MetricsManager.getInstance().getGauge("serviceName", "gaugeName");
		assertEquals(counter1, counter2);
		assertSame(counter1, counter2);
	}
	
	@Test
	public void testGetGauge_failure() {
		try {
			MetricsManager.getInstance().getGauge("wrongServiceName", "wrongGaugeName");
			fail("No exception occurred");
		}catch(RuntimeException e) {
			String expectedMessage = "Gauge with service name wrongServiceName and gauge name wrongGaugeName doesn't exist.";
			assertEquals(expectedMessage, e.getMessage());
		}
	}

}
