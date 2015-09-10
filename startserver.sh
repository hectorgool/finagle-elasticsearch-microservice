#!/bin/sh

#./activator assembly "run 9001"
#“nohup: Mantiene la ejecución de un comando pese a salir de la terminal” 
#nohup java -jar target/scala-2.11/finagle-elasticsearch-microservice-assembly-1.0-SNAPSHOT.jar 1> /dev/null 2>&1 &
rm RUNNING_PID
nohup java -jar target/scala-2.11/finagle-elasticsearch-microservice-assembly-1.0-SNAPSHOT.jar 1>> finagle.out 2>> finagle.err &
