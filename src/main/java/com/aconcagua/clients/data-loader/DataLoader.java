package com.aconcagua.dataloader;

import com.aconcagua.clients.vertx.ApiVerticle;
import org.apache.http.HttpHost;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.Index;
import org.elasticsearch.index.shard.ShardId;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.SearchException;
import org.elasticsearch.common.CheckedFunction;
import org.elasticsearch.common.Nullable;
import org.elasticsearch.common.ParseField;
import org.elasticsearch.common.collect.Tuple;
import org.elasticsearch.common.io.stream.StreamInput;
import org.elasticsearch.common.io.stream.StreamOutput;
import org.elasticsearch.common.io.stream.Writeable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class DataLoader {

    private static final Logger logger = LoggerFactory.getLogger(DataLoader.class);

    public static void main(String[] args) throws Exception {

        // create instance of RestHighLevelClient to perform bulk inserts,
        //  by executing the BulkRequest and to retrieve the BulkResponse
        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost("localhost", 9200, "http"))); // bind to 9200 port
        logger.info("Setting up data...");

        Map<String, Object> jsonMap = new HashMap<>();
        IndexRequest indexRequest; // index jsonMap into a specific index and make it searchable
        long search_base = 1000000000; // bulk 1000000000 documents
        String search_id;

        // to speed up atomic operations, execute multiple index, update and/or delete operations using a single bulk request 
        BulkRequest bulkRequest = new BulkRequest();
        ActionListener<BulkResponse> listener = new ActionListener<BulkResponse>() {
            @Override
            public void onResponse(BulkResponse bulkResponse) { // execution successfully completed 
                logger.info("Bulk insert ended. Status: {}", 
                        bulkResponse.status().getStatus());
            }
            @Override
            public void onFailure(Exception e) { // BulkRequest failed
                logger.info("Bulk insert failed: {}", e.getMessage());
            }
        };

        // bulk index 1000000000 documents, add bulk index items to request
        for(int i = 100; i < 1000; i++){
            search_id = Long.valueOf(search_base+i).toString();
            jsonMap.put("search_id", search_id);
            jsonMap.put("search_database_number", "SEARCH" + i);
            indexRequest = new IndexRequest("123456789-user")
                    .id(search_id).source(jsonMap);
            request.add(indexRequest);
            
            /* 
            
            Write every 1 MB or so..

            long limit = 1024 * 1024 * 2;
            long size = request.estimatedSizeInBytes();
            if(size > limit){
               logger.info("Request Size at iteration {} is {} bytes", i, size);
                try{
                   client.bulkAsync(request, RequestOptions.DEFAULT, listener);

                 }catch(IOException e){
                    e.printStackTrace();
                }
            } 
            */
        }

        try {
            logger.info("Loading now...");
            client.bulk(bulkRequest, RequestOptions.DEFAULT); // execute the bulk request
            // client.bulkAsync(request, RequestOptions.DEFAULT, listener);
            client.close();
            logger.info("Done!");
        } catch(Exception e){
            // e.printStackTrace();
            throw new IllegalStateException("Sorry, there was an error while performing bulk request.", e);
        }

    }
}

