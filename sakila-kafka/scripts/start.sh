#!/bin/bash

echo "Starting kafka"
$KAFKA_HOME/bin/kafka-server-start.sh /scripts/kafka.properties

#Create channels which need to be with non-default values here:
