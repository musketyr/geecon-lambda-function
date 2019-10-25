#!/bin/sh

./docker-build.sh

docker run -p 8080:8080 geecon-lambda-function