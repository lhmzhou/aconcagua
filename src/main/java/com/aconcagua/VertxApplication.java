package com.aconcagua;

import com.aconcagua.clients.vertx.ApiVerticle;

import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;

import org.apache.log4j.BasicConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

public class VertxApplication {

    private static final Logger logger = LoggerFactory.getLogger(VertxApp.class);

    public static void main(String[] args) {

        // BasicConfigurator.configure();
        VertxOptions options = new VertxOptions(); // create new vertx instance 

        // check for blocked threads every 10 s
        options.setBlockedThreadCheckInterval(10); // set the value of blocked thread check period to 10 s
        options.setBlockedThreadCheckIntervalUnit(TimeUnit.SECONDS);

        // warn if an event loop thread handler took more than 7 s to execute
        options.setMaxEventLoopExecuteTime(7);
        options.setMaxEventLoopExecuteTimeUnit(TimeUnit.SECONDS);

         // log the stack trace if an event loop or worker handler took more than 25s to execute
        options.setWarningExceptionTime(25);
        options.setWarningExceptionTimeUnit(TimeUnit.SECONDS);

        // options.setEventLoopPoolSize(2);
        
        Vertx vertx = Vertx.vertx(options);

        logger.info("Starting with default event loop thread pool size: " + 
                        VertxOptions.DEFAULT_EVENT_LOOP_POOL_SIZE); // default # event loop threads to be used = 2 * number of cores on the machine
        vertx.deployVerticle(new ApiVerticle().getClass().getName()); // deploy verticle
        logger.info("ApiVerticle setup for processing");
    }
}