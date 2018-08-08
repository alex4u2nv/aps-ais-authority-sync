#!/usr/bin/env bash

trap ctrl_c INT

ip_address=`ifconfig | grep "inet " | awk -F'[: ]+' '{ print $2 }' | grep -v 127`

function ctrl_c() {
        docker-compose down
}
#use 8180 for keycloak 3.4; and 8380 for keycloak 4.1
mvn  package -Dkeycloak.host=${ip_address} -Dkeycloak.port=8380
docker-compose up --build