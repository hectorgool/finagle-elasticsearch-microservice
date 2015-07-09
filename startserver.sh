#!/bin/sh

./activator assembly "run 9001"
java -jar target/scala-2.11/finagle-elasticsearch-microservice-assembly-1.0-SNAPSHOT.jar

#“nohup: Mantiene la ejecución de un comando pese a salir de la terminal” 
#nohup java -jar target/scala-2.11/finagle-elasticsearch-assembly-1.0-SANTO.jar > /dev/null 2>&1 &

