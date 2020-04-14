FROM openjdk:8-jre-alpine

ADD target/lib         /usr/share/sakila-store-write-service/lib

ARG JAR_FILE

ADD target/${JAR_FILE} /usr/share/sakila-store-write-service/sakila-store-write-service.jar

ENTRYPOINT ["/usr/bin/java", "-jar", "/usr/share/sakila-store-write-service/sakila-store-write-service.jar"]
