#!/bin/bash

# ./runsServer debug - to run in debug mode.

if [ $# -gt 0 ]
then
  echo "Starting server in debug mode"
  java -Xdebug -Xrunjdwp:server=y,transport=dt_socket,address=8000,suspend=n -jar target/adserver-campaign-springboot-0.0.1-SNAPSHOT.jar $@ 2>&1 > server.log &
else
  echo "Starting server in normal mode"
  mvn spring-boot:run 2>&1 > server.log &
fi
tail -f server.log
