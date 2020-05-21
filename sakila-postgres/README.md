# sakila-postgres

| Base image    | root user    | root password     |
|:--------------|:-------------|:------------------|
| `postgres:10` | `postgres`   | `dbaPass`         |


This is the `PostgreSQL 10` database for the `sakila-microservices` system. The database is 
initialised with these users and databases:


| Database             | user                         | password          |
|:---------------------|:-----------------------------|:------------------|
| `sakila_address`     | `sakila_address_service`     | `sakila_dba_pass` |
| `sakila_store_read`  | `sakila_store_read_service`  | `sakila_dba_pass` |
| `sakila_store_write` | `sakila_store_write_service` | `sakila_dba_pass` |
| `sakila_payment`     | `sakila_payment_service`     | `sakila_dba_pass` |

## Environment

The database is packaged into a `Docker` container using the Dockerfile in the root dir, which is used
to build an image on top of `postgres:10`.
