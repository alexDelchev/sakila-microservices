# sakila-zookeeper

This is the `ZooKeeper` service, which is a part of the `sakila-microservices` system. This service
backs the `Kafka` cluster of the system.

This service must be started for the `sakila-kafka`, `sakila-store-read-service` services are started.

## Environment

The service is packaged into a `Docker` container using the Dockerfile in the root dir, which is used
to build an image on top of `alpine:3.11.5`.

