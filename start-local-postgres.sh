#!/usr/bin/env bash

docker run --name micronaut-postgres -e POSTGRES_PASSWORD=micronaut -d -p 34321:5432 postgres