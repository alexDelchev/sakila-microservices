# sakila-microservices

A polyglot microservice system in the `JVM` language family based on the `sakila` database 
ported to `PostgreSQL` and `MongoDB`.

## Architecture

![Architecture Diagram](sakila-microservices-architecture.svg)

The original database is split into 5 separate databases: 

 - `address`
 - `store-write`
 - `store-read`
 - `payment`
 - `film`

Each database is completely managed by a microservice. The separate services communicate 
with each other by broadcasting and consuming messages when data change events occur. Each 
service exposes its data through a RESTful API. Requests are routed to the microservices using
an API gateway. A browser client consumes the composite API and visualises the data in 
different views.

The system is horizontally scalable and the instances of a microservice register themselves 
into the system using a discovery service. Requests to a given service are load-balanced
between the registered instances. Scheduled task execution is based on leader election 
between the instances.

Each component is packaged into a container. Components are isolated using networks for the
different database servers, the microservice layer and the gateway component, where a 
component is given access only to the networks which contain the services it requires to 
function.

Each of the microservices follows a MVC CRUD design, with the exception of the
(`sakila-store-write-service`, `sakila-store-read-service`) which combined together form the
"logical" store service using event sourcing and command-query responsibility segregation.
Here the `sakila-store-write` database acts as the event store, and the `sakila-store-read`
contains the resulting models.

## Technology stack

| service                    | application framework | language | testing framework | database connection  |
| -------------------------- |:---------------------:|:--------:|:-----------------:|:--------------------:|
| sakila-address-service     | `Spring Boot`         | `Groovy` | `Spock Framework` | `Hibernate`          |
| sakila-store-read-service  | `Spring Boot`         | `Java`   | `Spock Framework` | `JDBC`               |
| sakila-store-write-service | `Spring Boot`         | `Java`   | `Spock Framework` | `JDBC`               |
| sakila-payment-service     | `Spring Boot`         | `Kotlin` | `JUnit 5`         | `Hibernate`          |
| sakila-film-service        | `Spring Boot`         | `Java`   | `JUnit 5`         | `MongoDB Java Driver`|
 

The microservices are written in the JVM language family (`Java`, `Groovy`, `Kotlin`) with each
service utilising 1 language for the main/test sources respectively. The common application
framework for all of the services is `Spring Boot`. Automated testing is done using either 
`JUnit 5` or the `Spock Framework`.

The database layer of the system is composed of 1 `PostgreSQL` server and 1 `MongoDB` server.
Each database is versioned by its respective microservice using a database migration tool -
`Flyway` for `PostgreSQL` database and a custom migration tool implementation for the
`MongoDB` database. The services are connected to the `PostgreSQL` databases using either `JDBC`
or an ORM (`Hibernate`). The connection to the `MongoDB` database is made using the `MondgoDB`
java driver.

The RESTful api of each of the microservices is defined in an external `OpenAPI v2.0`
specification, which is used to generate the server/client code in the backend/frontend.

The services publish and consume data change events through `Kafka` streams. The accompanied 
`ZooKeeper` service is also used for leader election for scheduled task execution, this is done
using the `Apache Curator` library. Each service has a custom event bus implementation which is
used to asynchronously publish data change events internally, which are then published to the 
`Kafka` streams.

Service discovery is handled by `Spring Cloud Netflix Eureka`, where the system has 1 server 
and the separate services are clients. Instances of the microservices bind to a random port and
requests are load-balanced and routed to a given instance
through a `Spring Cloud Gateway` instance.

The frontend client uses `Angular 8` and the compiled application is served using `nginx`.
The client code which consumes the composite API is generated using the `ng-swagger-gen`
module.

`Maven` is used to build all of the components of the system. Each component is packaged into a 
Docker container, this is integrated into the build process using the `dockerfile-maven-plugin`.
The different networks are defined in the `docker-compose` configuration, which is also used
to start/stop the system.
Generated sources based on the `OpenAPI` specifications are generated using the 
`swagger-codegen-maven-plugin` plugin. `Groovy` sources are compiled using the `GMavenPlus`
maven plugin, and `Kotlin` sources are compiled using the `kotling-maven-plugin` plugin.

## How to run

Prerequisites to build & run the system are: 
 - `maven`
 - running `Docker` daemon
 - `docker-compose`
 

1 - Install the API specifications

`mvn -pl sakila-store-address-api-spec,sakila-store-store-read-api-spec,sakila-store-write-api-spec,sakila-payment-api-spec,sakila-film-api-spec -am clean install`

2 - Build the system

`mvn clean package`

3 - Start the system

`docker-compose up`

4 - Open the browser client

Visit `http://localhost/index.html` in your browser


## Project development

The project initially consisted of a java monolith backend that was connected to the `sakila` 
databases ported to `PostgreSQL`. This monolith was then split into 4 microservices, with the
database also split into 4. After this the `sakila-film` database was ported to `MongoDB` and
the store service was split into `sakila-store-read-service`, `sakila-store-write-service` 
in order to segregate the reading and writing operation responsibilities and implement
event sourcing. After this the `sakila-address-service` was rewritten in `Groovy`, and the
`sakila-payment-service` was rewritten in `Kotlin`.

## TODO

 - Increase test coverage
 - Consume full API from the browser client
