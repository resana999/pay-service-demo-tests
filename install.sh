#!/bin/bash

echo "1 - Запустить 2 - остановить все контейнеры"

function stopall() {
docker stop $(docker ps -qa)
}

function compose() {
mvn clean package
docker build -t sprinbootcore:dev .
docker-compose up -d --build
}

read inpt
if [ $inpt == '1' ]; then
    compose
fi

if [ $inpt == '2' ]; then
    stopall
fi