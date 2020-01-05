FROM openjdk:8-jre-alpine

ENTRYPOINT ["/usr/bin/java", "-jar", "/usr/share/sakila-film-service/sakila-film-service.jar"]

ADD target/lib           /usr/share/sakila-film-service/lib

ARG JAR_FILE
ADD target/${JAR_FILE} /usr/share/sakila-film-service/sakila-film-service.jar
