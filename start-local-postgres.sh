#!/usr/bin/env bash

docker run --name micronaut-postgres -e POSTGRES_PASSWORD=micronaut -e POSTGRES_DB=tasks -d -p 34321:5432 postgres

#