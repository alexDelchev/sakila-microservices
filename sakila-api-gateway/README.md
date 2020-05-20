# sakila-api-gateway

This is the gateway component of the `sakila-microservices` system. This 
services routes requests to a given microservice using load balancing.

The technology used is `spring-cloud-gateway` on top of a `Spring Boot`
server.

## Environment

The service is packaged into a `Docker` container using the Dockerfile in the root dir, which is used
to build an image on top of `openjdk:8-jre-alpine`.
