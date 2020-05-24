package com.aconcagua.clients.elasticsearch;

import org.apache.http.HttpHost;
import org.apache.http.util.EntityUtils;
import org.elasticsearch.client.Request;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.ResponseListener;
import org.elasticsearch.client.RestClient;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/*
 * ElasticRestAsyncClient make calls to Elasticsearch
 */
public class ElasticRestAsyncClient implements Runnable {

    public void run(){
        long start = System.currentTimeMillis();

        RestClient restClient = RestClient
                .builder(new HttpHost("localhost", 9200, "http")) 
                .build(); // build RestClient instance for restClient to connect to Elasticsearch cluster through HTTP

        Request request = new Request("GET", "/123456789-user/_search");
        request.addParameter("pretty", "true"); // search using query parameters
        System.out.println("[" + Thread.currentThread().getName() + "] " + "...inside ElasticRestAsyncClient");
        try {

            // support asychronous REST call requests
            restClient.performRequestAsync(request, new ResponseListener() {
                @Override
                public void onSuccess(Response response) { // ResponseListener notifies asychronous request success
                    try{
                        System.out.println("[" + Thread.currentThread().getName() + "] " + "Response >>>");
                        System.out.println(EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8));
                    }catch(IOException e){
                        System.out.println("Error");
                    }
                }
                @Override
                public void onFailure(Exception exception) { // ResponseListener notifies asychronous request failure
                    System.out.println("[" + Thread.currentThread().getName() + "] " + "...call failed");
                    exception.printStackTrace();
                }
            });

            // wait until the response is displayed...
            try {
                Thread.sleep(5000); 
            } catch (InterruptedException e){ // if any thread interrupts the current thread
                e.printStackTrace(); 
            }

            long end = System.currentTimeMillis();
            System.out.println("[" + Thread.currentThread().getName() + "] " + 
                                    "...ElasticRestAsyncClient ended after " +
                                    (end - start) + "ms");
            restClient.close(); // close client to free up resources
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}