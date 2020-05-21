# sakila-kafka

This is the `Kafka` service, which is a part of the `sakila-microservices` system. This service 
handles the data change event streams coming from the different services.

This service is preconfigured to connect to a `ZooKeeper` cluster with the following address:

`sakila-zookeeper:2181`

This means that the `ZooKeeper` service must be up and running before this one is started.

## Environment

The service is packaged into a `Docker` container using the Dockerfile in the root dir, which is used
to build an image on top of `alpine:3.11.5`.

