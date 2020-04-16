FROM openjdk:8-jre-alpine

ENTRYPOINT ["/usr/bin/java", "-jar", "/usr/share/sakila-store-service/sakila-store-service.jar"]

ADD target/lib           /usr/share/sakila-store-service/lib

ARG JAR_FILE
ADD target/${JAR_FILE} /usr/share/sakila-store-service/sakila-store-service.jar
