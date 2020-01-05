FROM openjdk:8-jre-alpine

ENTRYPOINT ["/usr/bin/java", "-jar", "/usr/share/sakila-payment-service/sakila-payment-service.jar"]

ADD target/lib           /usr/share/sakila-payment-service/lib

ARG JAR_FILE
ADD target/${JAR_FILE} /usr/share/sakila-payment-service/sakila-payment-service.jar
