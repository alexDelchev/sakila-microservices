FROM mongo:4.2.3

ENV MONGO_INITDB_ROOT_USERNAME sakila_dba
ENV MONGO_INITDB_ROOT_PASSWORD sakila_dba_pass
ENV MONGO_INITDB_DATABASE sakila-film

ADD ./scripts /docker-entrypoint-initdb.d/

VOLUME /data/db

EXPOSE 27017

CMD ["mongod"]
