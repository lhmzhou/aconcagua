package com.aconcagua.clients.vertx;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Route;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.client.HttpResponse;
import io.vertx.ext.web.client.WebClient;
import io.vertx.ext.web.codec.BodyCodec;
import io.vertx.core.Handler;
import io.vertx.core.eventbus.DeliveryOptions;
import io.vertx.core.eventbus.Message;
import io.vertx.core.json.JsonObject; 
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ApiVerticle extends AbstractVerticle {

    private static final Logger logger = LoggerFactory.getLogger(ApiVerticle.class);

    @Override
    public void start() throws Exception { // start HTTP server from within verticle

        HttpServer server = vertx.createHttpServer(); // create an HTTP server
        Router router = Router.router(vertx); // create router to take in HTTP request, find matching route for request
        Route testGetRoute = router.get("/api/test/"); 
        Route testPostRoute = router.post("/api/test");
        testPostRoute.produces("application/json");

        testGetRoute.handler(routingContext -> { // append route using handler
            long start = System.currentTimeMillis();
            logger.info("Arrived at API end point...calling Elasticsearch now");
            HttpServerResponse response = routingContext.response();
            response.setChunked(true); // required for sending stream of data back to client
            WebClient client = WebClient.create(vertx); // create web client for web server and HTTP request/response interactions 

            // send a HTTP GET request
            client.get(9200, "localhost", "/123456789-user/_search?pretty")
                    .as(BodyCodec.pipe(response)) // decoding large json bodies
                    .send(ar -> {
                        if (ar.succeeded()) {
                            long start1 = System.currentTimeMillis();
                            HttpResponse<Void> resp = ar.result(); // obtain response
                            //response.write(resp.bodyAsString());
                            long end1 = System.currentTimeMillis();
                            //response.end("<eom>.. took " + (end1 - start1) + "ms");
                        } else {
                            logger.error("Sorry, something went wrong: " + ar.cause().getMessage());
                            response.end("Error"); // end response with error statement
                        }
                    });

            //response.end("Reached API end point"); // don't end response as we want to send stream
            long end = System.currentTimeMillis();
            logger.info("Stream returned in " + (end - start ) + "ms"); 
        });

        // post with JSON body
        testPostRoute.handler(routingContext -> {
            routingContext.request().bodyHandler(body -> {
                JsonObject input = new JsonObject(body);
                 logger.info("Value for key: " + input.getString("name"));
            });
            HttpServerResponse response = routingContext.response();
            logger.info("Arrived at API endpoint via POST");
            JsonObject responseJson = new JsonObject();
            responseJson.put("Message", "Arrived at API end point via POST");
            response.end(responseJson.toString());
            //response.end("Arrived at API end point via POST");
        });

        server.requestHandler(router).listen(8081);
    }
}