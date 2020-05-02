package com.example.sakila.config

import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.kafka.common.serialization.StringSerializer
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.annotation.EnableKafka
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
import org.springframework.kafka.core.*

@Configuration
@EnableKafka
class KafkaConfig {

  @Value('${kafka.group-id}')
  private String groupId

  @Value('${kafka.broker.url}')
  private String brokerUrl

  @Bean
  ProducerFactory<String, String> producerFactory() {
    new DefaultKafkaProducerFactory<>(producerConfigurations())
  }

  @Bean
  Map<String, Object> producerConfigurations() {
    Map<String, Object> configurations = [:]

    configurations[ProducerConfig.BOOTSTRAP_SERVERS_CONFIG] =  brokerUrl
    configurations[ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG] = StringSerializer.class
    configurations[ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG] = StringSerializer.class

    configurations
  }

  @Bean
  ConsumerFactory<String, String> consumerFactory() {
    Map<String, Object> props = [:]

    props[ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG] =  brokerUrl
    props[ConsumerConfig.GROUP_ID_CONFIG] = groupId
    props[ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG] =  StringDeserializer
    props[ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG] =  StringDeserializer

    new DefaultKafkaConsumerFactory<>(props)
  }

  @Bean
  ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory() {
    ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>()
    factory.consumerFactory = consumerFactory()

    factory
  }

  @Bean
  KafkaTemplate<String, String> kafkaTemplate() {
    new KafkaTemplate<>(producerFactory())
  }
}
