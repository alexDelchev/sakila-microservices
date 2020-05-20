FROM openjdk:8-jre-alpine

ENTRYPOINT ["/usr/bin/java", "-jar", "/usr/share/sakila-address-service/sakila-address-service.jar"]

COPY target/lib           /usr/share/sakila-address-service/lib

COPY JAR_FILE
COPY target/${JAR_FILE} /usr/share/sakila-address-service/sakila-address-service.jar
