FROM openjdk:8-jre-alpine

ENTRYPOINT ["/usr/bin/java", "-jar", "/usr/share/sakila-payment-service/sakila-payment-service.jar"]

COPY target/lib           /usr/share/sakila-payment-service/lib

ARG JAR_FILE
COPY target/${JAR_FILE} /usr/share/sakila-payment-service/sakila-payment-service.jar
