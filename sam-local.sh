#!/bin/sh
docker build . -t geecon-lambda-function
mkdir -p build
docker run --rm --entrypoint cat geecon-lambda-function  /home/application/function.zip > build/function.zip

sam local start-api -t sam.yaml -p 3000

