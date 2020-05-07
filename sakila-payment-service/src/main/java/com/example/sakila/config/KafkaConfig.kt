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

@EnableKafka
@Configuration
class KafkaConfig(
    @Value("\${kafka.group-id}") private val groupId: String,
    @Value("\${kafka.broker.url}") private val brokerUrl: String
) {

  @Bean
  fun producerFactory(): ProducerFactory<String, String> {
    val properties: HashMap<String, kotlin.Any> = HashMap()

    properties[ProducerConfig.BOOTSTRAP_SERVERS_CONFIG] = brokerUrl
    properties[ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG] = StringSerializer::class.java
    properties[ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG] = StringSerializer::class.java

    return DefaultKafkaProducerFactory(properties)
  }

  @Bean
  fun consumerFactory(): ConsumerFactory<String, String> {
    val properties: HashMap<String, kotlin.Any> = HashMap()

    properties[ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG] = brokerUrl
    properties[ConsumerConfig.GROUP_ID_CONFIG] = groupId
    properties[ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG] = StringDeserializer::class.java
    properties[ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG] = StringDeserializer::class.java

    return DefaultKafkaConsumerFactory(properties)
  }

  @Bean
  fun kafkaListenerContainerFactory(): ConcurrentKafkaListenerContainerFactory<String, String> {
    val containerFactory = ConcurrentKafkaListenerContainerFactory<String, String>()

    containerFactory.consumerFactory = consumerFactory()

    return containerFactory
  }

  @Bean
  fun kafkaTemplate(): KafkaTemplate<String, String> = KafkaTemplate(producerFactory())
}
