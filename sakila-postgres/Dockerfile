FROM postgres:10

VOLUME /var/lib/postgresql/data

COPY --chown=root ./scripts /docker-entrypoint-initdb.d

ENV POSTGRES_PASSWORD dbaPass

EXPOSE 5432

CMD ["postgres"]
