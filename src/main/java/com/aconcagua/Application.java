package com.aconcagua;

import com.aconcagua.clients.elasticsearch.ElasticRestAsyncClient;
import com.aconcagua.clients.elasticsearch.ElasticRestClient;

import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Application {
    /** 
     * main thread will act as the trigger thread, leading to java future
     * after waiting for some time, retrieve data from the future object
     */
    public static void main(String[] args) throws Exception {
        // get time before the operation is executed
        long start = System.currentTimeMillis();
        System.out.println( "[" + Thread.currentThread().getName() + "] " + "...trigger started");
        /*
        ExecutorService executorService = Executors.newFixedThreadPool(4);
        executorService.execute(new ElasticRestClient());
        executorService.execute(new ElasticRestAsyncClient());
        executorService.shutdown();
        //CompletableFutureClient.call();
        */
        //VertxClient.call()
        // get the time after the operation is executed
        long end = System.currentTimeMillis();
        System.out.println( "[" + Thread.currentThread().getName() + "] " + 
                                "...trigger completed in " + 
                                (end - start) +"ms");
    }
}