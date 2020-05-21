# sakila-mongodb

| Base image    | root user    | root password     |
|:--------------|:-------------|:------------------|
| `mongo:4.2.3` | `sakila_dba` | `sakila_dba_pass` |

This is the mongodb database which is used in the `sakila-microservices` system.

The database comes with a pre-created database called `sakila_film` with a user
for the `sakila-film-service`.


## Environment

The database is packaged into a `Docker` container using the Dockerfile in the root dir, which is used
to build an image on top of `mongo:4.2.3`.

