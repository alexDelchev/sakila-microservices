FROM amazoncorretto:17.0.6-alpine3.17 as jdk-builder

RUN apk add --no-cache binutils

RUN $JAVA_HOME/bin/jlink \
    --verbose \
    --add-modules java.base,java.compiler,java.desktop,java.instrument,java.management,java.prefs,java.rmi,java.scripting,java.security.jgss,java.security.sasl,java.sql.rowset,jdk.httpserver,jdk.jfr,jdk.unsupported \
    --strip-debug \
    --no-man-pages \
    --no-header-files \
    --compress=2 \
    --output /jre

FROM alpine:3.17
ENV JAVA_HOME=/jre
ENV PATH="${JAVA_HOME}/bin:${PATH}"

COPY --from=jdk-builder /jre $JAVA_HOME

RUN adduser --no-create-home -u 1000 -D service

RUN mkdir /usr/share/sakila-store-read-service && \
    chown -R service /usr/share/sakila-store-read-service

USER 1000

COPY --chown=1000:1000 target/lib /usr/share/sakila-store-read-service/lib

ARG JAR_FILE
COPY --chown=1000:1000 target/${JAR_FILE} /usr/share/sakila-store-read-service/sakila-store-read-service.jar

ENTRYPOINT ["/jre/bin/java", "-jar", "/usr/share/sakila-store-read-service/sakila-store-read-service.jar"]

