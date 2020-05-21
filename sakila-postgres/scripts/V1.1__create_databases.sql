CREATE DATABASE sakila_address
    WITH
    OWNER = postgres
    ENCODING = 'UTF8'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1;

GRANT ALL ON DATABASE sakila_address TO postgres;

GRANT TEMPORARY, CONNECT ON DATABASE sakila_address TO PUBLIC;

GRANT ALL ON DATABASE sakila_address TO sakila_address_service;


CREATE DATABASE sakila_payment
    WITH
    OWNER = postgres
    ENCODING = 'UTF8'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1;



CREATE DATABASE sakila_store_read
    WITH
    OWNER = postgres
    ENCODING = 'UTF8'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1;

GRANT ALL ON DATABASE sakila_store_read TO sakila_store_read_service;

GRANT ALL ON DATABASE sakila_store_read TO postgres;

GRANT TEMPORARY, CONNECT ON DATABASE sakila_store_read TO PUBLIC;

CREATE DATABASE sakila_store_write
    WITH
    OWNER = postgres
    ENCODING = 'UTF8'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1;

GRANT ALL ON DATABASE sakila_store_write TO sakila_store_write_service;

GRANT ALL ON DATABASE sakila_store_write TO postgres;

GRANT TEMPORARY, CONNECT ON DATABASE sakila_store_write TO PUBLIC;
