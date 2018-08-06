#!/usr/bin/env bash

trap ctrl_c INT

function ctrl_c() {
        docker-compose down
}
docker-compose down
mvn  package
docker-compose up --build