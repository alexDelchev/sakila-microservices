FROM amazoncorretto:17.0.6-alpine3.17 as jdk-builder

RUN apk add --no-cache binutils

RUN $JAVA_HOME/bin/jlink \
    --verbose \
    --add-modules java.base,java.desktop,java.instrument,java.naming,java.prefs,java.rmi,java.scripting,java.security.jgss,java.sql,jdk.httpserver,jdk.jfr,jdk.management,jdk.unsupported \
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

RUN mkdir /usr/share/sakila-discovery-service && \
    chown -R service /usr/share/sakila-discovery-service

USER 1000

COPY --chown=1000:1000 target/lib /usr/share/sakila-discovery-service/lib

ARG JAR_FILE
COPY --chown=1000:1000 target/${JAR_FILE} /usr/share/sakila-discovery-service/sakila-discovery-service.jar

ENTRYPOINT ["/jre/bin/java", "-jar", "/usr/share/sakila-discovery-service/sakila-discovery-service.jar"]
