# aconcagua

Interactions with the database can be made asynchronously simpler. `aconcagua` shows how to asynchronously feed data into Elasticsearch using Vert.x reactive single-event loop.

## Prerequisites

Vertx 3.7.x
</br>
Elasticsearch 7.5.x
</br>
Java 8+ 
</br> 
Maven 2.5.x

## Build

Make sure the Elasticsearch database is up and humming along. On a Mac, you may elect to install via brew:
```
brew update
brew install elasticsearch
```

Package your application
```
./mvn clean package
```

Run your application
```
./mvn clean compile exec:java
```

Run source Verticle without compiling
```
vertx run VertxApplication.java
```

## Contributing

Please [open an issue](https://github.com/lhmzhou/aconcagua/issues/new) to discuss plans before doing any work on `aconcagua`


## More Geekery

[Vertx Docs](https://vertx.io/docs/)
</br>
[Vertx API Manuel](https://vertx.io/vertx2/core_manual_groovy.html#replying-to-messages)
</br>
[Elasticsearch](https://www.elastic.co/guide/index.html)
</br>
[REST calls made easy](https://qbox.io/blog/rest-calls-new-java-elasticsearch-client-tutorial)
</br>
[Bulk uploading](https://www.compose.com/articles/compose-elasticsearch-bulk-uploading-and-the-high-level-java-rest-client-part-1/)
