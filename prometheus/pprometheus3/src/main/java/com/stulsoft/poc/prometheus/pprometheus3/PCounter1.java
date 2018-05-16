package com.stulsoft.poc.prometheus.pprometheus3;

import io.prometheus.client.Counter;

/**
 * @author Yuriy Stul
 * @since 15 May 2018
 *
 */
public class PCounter1 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("==>main");
		System.out.println("c");
		Counter c = Counter
				.build("name", "help")
				.labelNames("method")
				.register();
		c.labels("get").inc();
		c.labels("put").inc();

		c.collect().forEach(mfs -> {
			System.out.println("name: " + mfs.name);	
			System.out.println("type: " + mfs.type);
			System.out.println("Samples:");
			mfs.samples.forEach(sample->{
				System.out.println("labelNames: " + sample.labelNames);
				System.out.println("value: " + sample.value);
				
			});	
		});

		System.out.println("\nc1");
		Counter c1 = Counter
				.build("name1", "help1")
				.labelNames("labels")
				.register();
		c1.labels("get").inc();
		c1.labels("put").inc(3);

		c1.collect().forEach(mfs -> {
			System.out.println("name: " + mfs.name);	
			System.out.println("type: " + mfs.type);
			System.out.println("Samples:");
			mfs.samples.forEach(sample->{
				System.out.println("labelNames: " + sample.labelNames);
				System.out.println("labelValues: " + sample.labelValues);
				sample.labelValues.forEach(labelValue->System.out.println("labelValue: " + labelValue));
				System.out.println("value: " + sample.value);
			});	
		});

		System.out.println("\nc2");
		Counter c2 = Counter
				.build("name2", "help2")
				.register();
		c2.inc(3);

		c2.collect().forEach(mfs -> {
			System.out.println("name: " + mfs.name);	
			System.out.println("type: " + mfs.type);
			System.out.println("Samples:");
			mfs.samples.forEach(sample->{
				System.out.println("labelNames: " + sample.labelNames);
				System.out.println("labelValues: " + sample.labelValues);
				sample.labelValues.forEach(labelValue->System.out.println("labelValue: " + labelValue));
				System.out.println("value: " + sample.value);
			});	
		});

		System.out.println("<==main");
	}

}
