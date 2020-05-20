FROM openjdk:8-jre-alpine

ENTRYPOINT ["/usr/bin/java", "-jar", "/usr/share/sakila-api-gateway/sakila-api-gateway.jar"]

COPY target/lib           /usr/share/sakila-api-gateway/lib

ARG JAR_FILE
COPY target/${JAR_FILE} /usr/share/sakila-api-gateway/sakila-api-gateway.jar
