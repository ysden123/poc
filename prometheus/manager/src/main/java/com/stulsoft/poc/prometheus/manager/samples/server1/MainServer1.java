/**
 * Created by Yuriy Stul 17 May 2018
 */
package com.stulsoft.poc.prometheus.manager.samples.server1;

import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.vertx.core.Verticle;
import io.vertx.core.Vertx;

/**
 * @author Yuriy Stul
 *
 */
public class MainServer1 {
	private static Logger logger = LoggerFactory.getLogger(MainServer1.class);

	public static void main(String[] args) {
		logger.info("start");
		Vertx vertx = Vertx.vertx();

		Verticle service = new Service1();
		vertx.deployVerticle(service);

		Verticle httpServer = new HttpServer();

		logger.info("Deploying HttpServer");
		vertx.deployVerticle(httpServer);

		System.out.println("For end enter any line");
		Scanner sc = new Scanner(System.in);
		sc.next();
		sc.close();

		logger.info("Stopping Vertx...");
		vertx.close();
		logger.info("Stopped Main");

	}
}
