#!/bin/bash

#docker-compose -f -d docker/docker-compose.yml up perry ferb db2 db

#while ! docker ps --filter "name=docker_ferb_1" | grep "healthy" | wc -l;
#do
#  echo sleeping;
#  sleep 1;
#done;

#docker-compose -f docker/docker-compose.yml ferb-test
#docker-compose -f docker/docker-compose.yml down

docker-compose -f docker/docker-compose.yml up -d
ret=$(docker wait docker_ferb-test_1)
docker-compose -f docker/docker-compose.yml down
exit $ret