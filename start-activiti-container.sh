#!/usr/bin/env bash

ip_address=`ifconfig | grep "inet " | awk -F'[: ]+' '{ print $2 }' | grep -v 127`
mvn clean package -Dkeycloak.host=${ip_address} -Dkeycloak.port=8380
docker-compose up --build