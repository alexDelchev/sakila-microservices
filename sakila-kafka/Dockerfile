FROM alpine:3.11.5

ENV KAFKA_HOME /usr/local/kafka

COPY /scripts/start.sh /scripts/
RUN chmod a+x /scripts/start.sh

COPY /scripts/kafka.properties /scripts/

RUN apk add --no-cache openjdk8-jre && \
    apk add --no-cache wget && \
    apk add --no-cache bash

RUN wget https://archive.apache.org/dist/kafka/2.4.1/kafka_2.13-2.4.1.tgz && \
    tar -xzf kafka_2.13-2.4.1.tgz && \
    mv kafka_2.13-2.4.1 $KAFKA_HOME

EXPOSE 9092 9093

ENTRYPOINT ["/scripts/start.sh"]
