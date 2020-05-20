FROM openjdk:8-jre-alpine

ENTRYPOINT ["/usr/bin/java", "-jar", "/usr/share/sakila-discovery-server/sakila-discovery-server.jar"]

COPY target/lib           /usr/share/sakila-discovery-server/lib

ARG JAR_FILE
COPY target/${JAR_FILE} /usr/share/sakila-discovery-server/sakila-discovery-server.jar
