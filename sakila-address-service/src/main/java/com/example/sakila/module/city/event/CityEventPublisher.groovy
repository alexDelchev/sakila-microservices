package com.example.sakila.module.city.event

import com.example.sakila.event.bus.EventBus
import com.example.sakila.event.bus.Handler
import com.example.sakila.module.city.event.model.CityCreatedEvent
import com.example.sakila.module.city.event.model.CityDeletedEvent
import com.example.sakila.module.city.event.model.CityUpdatedEvent
import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component

import javax.annotation.PostConstruct

@Slf4j
@Component
class CityEventPublisher {

  private static final String CITY_CREATED_TOPIC = 'sakila-address-city-created'

  private static final String CITY_UPDATED_TOPIC = 'sakila-address-city-updated'

  private static final String CITY_DELETED_TOPIC = 'sakila-address-city-deleted'

  private final ObjectMapper objectMapper

  private final EventBus eventBus

  private final KafkaTemplate<String, String> kafkaTemplate

  @Autowired
  CityEventPublisher(
      @Qualifier('CityEventBus') EventBus eventBus,
      KafkaTemplate kafkaTemplate,
      ObjectMapper objectMapper
  ) {
    this.eventBus = eventBus
    this.kafkaTemplate = kafkaTemplate
    this.objectMapper = objectMapper
  }

  @PostConstruct
  private postConstruct() {
    eventBus.register(this)
  }

  @Handler
  void onCityCreatedEvent(CityCreatedEvent event) {
    publish(CITY_CREATED_TOPIC, event)
  }

  @Handler
  void onCityUpdatedEvent(CityUpdatedEvent event) {
    publish(CITY_UPDATED_TOPIC, event)
  }

  @Handler
  void onCityDeletedEvent(CityDeletedEvent event) {
    publish(CITY_DELETED_TOPIC, event)
  }

  private void publish(String topic, Object value) {
    String serializedMessage = serialize(value)
    log.info("Publishing to (${topic}): ${serializedMessage}");
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
