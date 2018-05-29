/*
 * Created by Yuriy Stul 29 May 2018
 */
package com.stulsoft.poc.service.management.quartz1;

import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Verticle;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;

/**
 * @author Yuriy Stul
 *
 */
public class Quartz1MainVerticle extends AbstractVerticle {
	private static final Logger logger = LoggerFactory.getLogger(Quartz1MainVerticle.class);

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		logger.info("==>Quartz1MainVerticle.main");
		VertxOptions vertxOptions = new VertxOptions()
				.setBlockedThreadCheckInterval(1000000);
		Vertx vertx = Vertx.vertx(vertxOptions);
		Quartz1MainVerticle quartz1MainVerticle = new Quartz1MainVerticle();
		quartz1MainVerticle.deployVerticles(vertx);
		
		System.out.println("Enter a line to exit");
		
		Scanner sc = new Scanner(System.in);
        sc.next();
        sc.close();

        logger.info("Stopping Vertx...");
        vertx.close();

	}
	
	private void deployVerticles(final Vertx vertx) {
		logger.info("Deploying verticles...");
		Verticle servive1 = new Service1();
		
		logger.info("Deploying verticle");
		DeploymentOptions deploymentOptions = new DeploymentOptions()
				.setWorker(true)
				.setMaxWorkerExecuteTime(1000 * 1000 * 60 * 4);
		vertx.deployVerticle(servive1, deploymentOptions);
		
		vertx.deployVerticle(new SchedulerVerticle());
	}

	/* (non-Javadoc)
	 * @see io.vertx.core.AbstractVerticle#start()
	 */
	@Override
	public void start() throws Exception {
		logger.info("Starting Quartz1MainVerticle ...");
		super.start();
	}

	/* (non-Javadoc)
	 * @see io.vertx.core.AbstractVerticle#stop()
	 */
	@Override
	public void stop() throws Exception {
		logger.info("Stopping Quartz1MainVerticle ...");
		super.stop();
	}
	
	

}
