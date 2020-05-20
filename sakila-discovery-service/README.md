# sakila-discovery-service

This is the discover service component of the `sakila-microservices` system.

The technology used is the `spring-netflix-eureka-server` on top of a 
`Spring Boot` server.

## Environment

The service is packaged into a `Docker` container using the Dockerfile in the root dir, which is used
to build an image on top of `openjdk:8-jre-alpine`.
