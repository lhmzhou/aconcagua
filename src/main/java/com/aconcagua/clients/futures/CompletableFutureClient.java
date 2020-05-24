package com.aconcagua.clients.futures;

import com.aconcagua.clients.elasticsearch.ElasticRestAsyncClient;
import com.aconcagua.clients.vertx.ApiVerticle;
import org.apache.http.HttpHost;
import org.apache.http.util.EntityUtils;
import org.elasticsearch.client.Request;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.ResponseListener;
import org.elasticsearch.client.RestClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

/**
 * Wraps the performRequestAsync call to provide a Stream to the calling client
 * Sends the HTTP request asynchronously to Elasticsearch
 */
public class CompletableFutureClient {

    private static final Logger logger = LoggerFactory.getLogger(CompletableFutureClient.class);

    public Future<String> call() throws InterruptedException {

        long start = System.currentTimeMillis();

        // create a builder instance using localhost to where restClient's request will be sent 
        RestClient restClient = RestClient
                .builder(new HttpHost("localhost", 9200, "http"))
                .build();

        Request request = new Request("GET", "/123456789-user/_search");
        request.addParameter("pretty", "true");
        logger.info("inside CompletableFutureClient");

        // performRequestAsync sends a request to Elasticsearch cluster that restClient points to
        try {
            // run pending task asynchronously, ResponseListener is notified when request is completed
            restClient.performRequestAsync(request, new ResponseListener() {
                @Override
                public void onSuccess(Response response) { // in case of success
                    try{
                        logger.info("Response >>>");
                        EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
                    } catch(IOException e){
                        logger.error("Error!");
                    }
                }
                @Override
                public void onFailure(Exception exception) { // in case of failure
                    logger.error("...call failed");
                    exception.printStackTrace();
                }
            });

            // Wait until the response is displayed...
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e){ 
                e.printStackTrace();
            }

            restClient.close(); // close client
        } catch (IOException e) { // communication problem ie. socket timeout
            e.printStackTrace();
        }

        long end = System.currentTimeMillis();
        logger.info("...ElasticRestAsyncClient ended after " + (end - start) + "ms");
        
        return new CompletableFuture<String>();
    }

}