#!/bin/bash

echo "Starting zookeeper with properties:"
cat $KAFKA_HOME/config/zookeeper.properties
$KAFKA_HOME/bin/zookeeper-server-start.sh $KAFKA_HOME/config/zookeeper.properties
