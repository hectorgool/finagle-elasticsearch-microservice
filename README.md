This is your new Play application
=================================

Finagle +  Elasticsearch 




what-depends-on


[error] (*:assembly) deduplicate: different file contents found in the following:
[error] /path/.ivy2/cache/org.slf4j/jcl-over-slf4j/jars/jcl-over-slf4j-1.7.12.jar:org/apache/commons/logging/Log.class
[error] /path/.ivy2/cache/commons-logging/commons-logging/jars/commons-logging-1.1.1.jar:org/apache/commons/logging/Log.class
[error]


dependency-dot

what-depends-on org.slf4j jcl-over-slf4j 1.7.12

[info] org.slf4j:jcl-over-slf4j:1.7.12
[info]   +-com.typesafe.play:play-netty-utils:2.4.2
[info]   | +-com.typesafe.play:play_2.11:2.4.2 [S]
[info]   |   +-com.typesafe.play:filters-helpers_2.11:2.4.2 [S]
[info]   |   | +-finagle-elasticsearch-microservice:finagle-elasticsearch-microservice_2.11:1.0-SNAPSHOT [S]
[info]   |   | 
[info]   |   +-com.typesafe.play:play-cache_2.11:2.4.2 [S]
[info]   |   | +-finagle-elasticsearch-microservice:finagle-elasticsearch-microservice_2.11:1.0-SNAPSHOT [S]
[info]   |   | 
[info]   |   +-com.typesafe.play:play-jdbc-api_2.11:2.4.2 [S]
[info]   |   | +-com.typesafe.play:play-jdbc_2.11:2.4.2 [S]
[info]   |   |   +-finagle-elasticsearch-microservice:finagle-elasticsearch-microservice_2.11:1.0-SNAPSHOT [S]
[info]   |   |   
[info]   |   +-com.typesafe.play:play-server_2.11:2.4.2 [S]
[info]   |   | +-com.typesafe.play:play-netty-server_2.11:2.4.2 [S]
[info]   |   | | +-finagle-elasticsearch-microservice:finagle-elasticsearch-microservice_2.11:1.0-SNAPSHOT [S]
[info]   |   | | 
[info]   |   | +-finagle-elasticsearch-microservice:finagle-elasticsearch-microservice_2.11:1.0-SNAPSHOT [S]
[info]   |   | 
[info]   |   +-com.typesafe.play:play-ws_2.11:2.4.2 [S]
[info]   |     +-finagle-elasticsearch-microservice:finagle-elasticsearch-microservice_2.11:1.0-SNAPSHOT [S]
[info]   |     
[info]   +-com.typesafe.play:play_2.11:2.4.2 [S]
[info]     +-com.typesafe.play:filters-helpers_2.11:2.4.2 [S]
[info]     | +-finagle-elasticsearch-microservice:finagle-elasticsearch-microservice_2.11:1.0-SNAPSHOT [S]
[info]     | 
[info]     +-com.typesafe.play:play-cache_2.11:2.4.2 [S]
[info]     | +-finagle-elasticsearch-microservice:finagle-elasticsearch-microservice_2.11:1.0-SNAPSHOT [S]
[info]     | 
[info]     +-com.typesafe.play:play-jdbc-api_2.11:2.4.2 [S]
[info]     | +-com.typesafe.play:play-jdbc_2.11:2.4.2 [S]
[info]     |   +-finagle-elasticsearch-microservice:finagle-elasticsearch-microservice_2.11:1.0-SNAPSHOT [S]
[info]     |   
[info]     +-com.typesafe.play:play-server_2.11:2.4.2 [S]
[info]     | +-com.typesafe.play:play-netty-server_2.11:2.4.2 [S]
[info]     | | +-finagle-elasticsearch-microservice:finagle-elasticsearch-microservice_2.11:1.0-SNAPSHOT [S]
[info]     | | 
[info]     | +-finagle-elasticsearch-microservice:finagle-elasticsearch-microservice_2.11:1.0-SNAPSHOT [S]
[info]     | 
[info]     +-com.typesafe.play:play-ws_2.11:2.4.2 [S]
[info]       +-finagle-elasticsearch-microservice:finagle-elasticsearch-microservice_2.11:1.0-SNAPSHOT [S]
[info]       
[success] Total time: 0 s, completed 9/07/2015 03:44:54 PM



what-depends-on org.apache.httpcomponents httpclient 4.0.1

[info] org.apache.httpcomponents:httpclient:4.0.1
[info]   +-oauth.signpost:signpost-commonshttp4:1.2.1.2
[info]     +-com.typesafe.play:play-ws_2.11:2.4.2 [S]
[info]       +-finagle-elasticsearch-microservice:finagle-elasticsearch-microservice_2.11:1.0-SNAPSHOT [S]
[info]       
[success] Total time: 0 s, completed 9/07/2015 03:44:07 PM



libraryDependencies ++= Seq(
  jdbc,
  cache,
  ws exclude ("org.apache.httpcomponents" , "httpclient"),
  specs2 % Test,
  "com.twitter"                 % "finagle-http_2.11"       % "6.25.0",
  "com.twitter"                 % "bijection-util_2.11"     % "0.7.2"
)

