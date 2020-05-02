package com.example.sakila.module.country.event

import com.example.sakila.event.bus.EventBus
import com.example.sakila.event.bus.Handler
import com.example.sakila.module.country.event.model.CountryCreatedEvent
import com.example.sakila.module.country.event.model.CountryDeletedEvent
import com.example.sakila.module.country.event.model.CountryUpdatedEvent
import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component

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
    String serialiedMessage = serialize(event)
    kafkaTemplate.send(COUNTRY_CREATED_TOPIC, serialiedMessage)
  }

  @Handler
  void onCountryUpdatedEvent(CountryUpdatedEvent event) {
    String serializedMessage = serialize(event)
    kafkaTemplate.send(COUNTRY_UPDATED_TOPIC, serializedMessage)
  }

  @Handler
  void onCountryDeletedEvent(CountryDeletedEvent event) {
    String serializedMessage = serialize(event)
    kafkaTemplate.send(COUNTRY_DELETED_TOPIC, serializedMessage)
  }

  private String serialize(Object object) {
    try {
      return objectMapper.writeValueAsString(object)
    } catch (JsonProcessingException e) {
      throw new IllegalArgumentException(e)
    }
  }
}
