FROM openjdk:8-jre-alpine

ENTRYPOINT ["/usr/bin/java", "-jar", "/usr/share/sakila-store-service/sakila-store-service.jar"]

COPY target/lib           /usr/share/sakila-store-service/lib

ARG JAR_FILE
COPY target/${JAR_FILE} /usr/share/sakila-store-service/sakila-store-service.jar
