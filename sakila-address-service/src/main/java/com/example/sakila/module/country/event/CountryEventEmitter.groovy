package com.example.sakila.module.country.event

import com.example.sakila.event.bus.EventBus
import com.example.sakila.event.bus.Handler
import com.example.sakila.module.country.event.model.CountryCreatedEvent
import com.example.sakila.module.country.event.model.CountryDeletedEvent
import com.example.sakila.module.country.event.model.CountryUpdatedEvent
import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component

@Slf4j
@Component
class CountryEventEmitter {

  private static final String COUNTRY_CREATED_TOPIC = 'sakila-address-country-created'

  private static final String COUNTRY_UPDATED_TOPIC = 'sakila-address-country-updated'

  private static final String COUNTRY_DELETED_TOPIC = 'sakila-address-country-deleted'

  private final ObjectMapper objectMapper = new ObjectMapper()

  private final EventBus eventBus

  private final KafkaTemplate<String, String> kafkaTemplate

  @Autowired
  CountryEventEmitter(@Qualifier('CountryEventBus') EventBus eventBus, KafkaTemplate kafkaTemplate) {
    this.eventBus = eventBus
    this.kafkaTemplate = kafkaTemplate

    this.eventBus.register(this)
  }

  @Handler
  void onCountryCreatedEvent(CountryCreatedEvent event) {
    publish(COUNTRY_CREATED_TOPIC, event)
  }

  @Handler
  void onCountryUpdatedEvent(CountryUpdatedEvent event) {
    publish(COUNTRY_UPDATED_TOPIC, event)
  }

  @Handler
  void onCountryDeletedEvent(CountryDeletedEvent event) {
    publish(COUNTRY_DELETED_TOPIC, event)
  }

  private publish(String topic, Object value) {
    String serializedMessage = serialize(value)
    log.info("Publishing to (${topic}): ${serializedMessage}")
    kafkaTemplate.send(topic, serializedMessage)
  }

  private String serialize(Object object) {
    try {
      return objectMapper.writeValueAsString(object)
    } catch (JsonProcessingException e) {
      throw new IllegalArgumentException(e)
    }
  }
}
