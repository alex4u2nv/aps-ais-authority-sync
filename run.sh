#!/usr/bin/env bash

trap ctrl_c INT

ip_address=`ifconfig | grep "inet " | awk -F'[: ]+' '{ print $2 }' | grep -v 127`

function ctrl_c() {
        docker-compose down
}

mvn  package -Dkeycloak.host=${ip_address} -Dkeycloak.port=8180
docker-compose up --build