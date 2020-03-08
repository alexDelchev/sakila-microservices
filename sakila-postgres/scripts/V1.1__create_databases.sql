CREATE DATABASE sakila_address
    WITH
    OWNER = postgres
    ENCODING = 'UTF8'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1;

GRANT ALL ON DATABASE sakila_address TO postgres;

GRANT TEMPORARY, CONNECT ON DATABASE sakila_address TO PUBLIC;

GRANT ALL ON DATABASE sakila_address TO sakila_address_service;



CREATE DATABASE sakila_film
    WITH
    OWNER = postgres
    ENCODING = 'UTF8'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1;

GRANT ALL ON DATABASE sakila_film TO postgres;

GRANT TEMPORARY, CONNECT ON DATABASE sakila_film TO PUBLIC;

GRANT ALL ON DATABASE sakila_film TO sakila_film_service;



CREATE DATABASE sakila_payment
    WITH
    OWNER = postgres
    ENCODING = 'UTF8'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1;



CREATE DATABASE sakila_store
    WITH
    OWNER = postgres
    ENCODING = 'UTF8'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1;

GRANT ALL ON DATABASE sakila_store TO sakila_store_service;

GRANT ALL ON DATABASE sakila_store TO postgres;

GRANT TEMPORARY, CONNECT ON DATABASE sakila_store TO PUBLIC;
