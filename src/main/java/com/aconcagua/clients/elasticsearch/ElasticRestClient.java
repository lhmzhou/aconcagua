package com.aconcagua.clients.elasticsearch;

import org.apache.http.*;
import org.apache.http.HttpHost;
import org.apache.http.RequestLine;
import org.apache.http.util.EntityUtils;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.entity.StringEntity;
import org.elasticsearch.client.Request;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;

import java.io.IOException;

/**
 * ElasticRestClient implements runnable interface and task of executing threads
 */
public class ElasticRestClient implements Runnable {

    @Override
    public void run(){
        
        long start = System.currentTimeMillis();
        RestClient restClient = RestClient
                .builder(new HttpHost("localhost", 9200, "http"))
                .build();

        Request request = new Request("GET", "/123456789-user/_search"); // stream request 
        request.addParameter("pretty", "true");

        // print executing thread
        System.out.println("[" + Thread.currentThread().getName() + "] " + "...made it into the ElasticRestClient");
        try {
            Response response = restClient.performRequest(request);
            RequestLine requestLine = response.getRequestLine();
            String responseBody = EntityUtils.toString(response.getEntity());
            System.out.println("[" + Thread.currentThread().getName() + "] " + "Request  >>>>>>>>> " + requestLine
                    .toString());
            System.out.println("[" + Thread.currentThread().getName() + "] " + "Response >>>>>>>>> ");
            System.out.println("[" + Thread.currentThread().getName() + "] " + responseBody);
            long end = System.currentTimeMillis();
            System.out.println("[" + Thread.currentThread().getName() + "] " + "...ElasticRestClient ended after " +
                    (end - start) + "ms");
            restClient.close(); // close stream 
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}  