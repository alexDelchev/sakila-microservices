FROM openjdk:8-jre-alpine

ENTRYPOINT ["/usr/bin/java", "-jar", "/usr/share/sakila-address-service/sakila-address-service.jar"]

ADD target/lib           /usr/share/sakila-address-service/lib

ARG JAR_FILE
ADD target/${JAR_FILE} /usr/share/sakila-address-service/sakila-address-service.jar
