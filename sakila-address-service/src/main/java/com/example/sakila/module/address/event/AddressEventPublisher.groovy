package com.example.sakila.module.address.event

import com.example.sakila.event.bus.EventBus
import com.example.sakila.event.bus.Handler
import com.example.sakila.module.address.event.model.AddressCreatedEvent
import com.example.sakila.module.address.event.model.AddressDeletedEvent
import com.example.sakila.module.address.event.model.AddressUpdatedEvent
import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component

@Slf4j
@Component
class AddressEventPublisher {

  private static final String ADDRESS_CREATED_TOPIC = 'sakila-address-address-created'

  private static final String ADDRESS_UPDATED_TOPIC = 'sakila-address-address-updated'

  private static final String ADDRESS_DELETED_TOPIC = 'sakila-address-address-deleted'

  private final ObjectMapper objectMapper = new ObjectMapper()

  private final EventBus eventBus

  private final KafkaTemplate<String, String> kafkaTemplate

  @Autowired
  AddressEventPublisher(@Qualifier('AddressEventBus') EventBus eventBus, KafkaTemplate kafkaTemplate) {
    this.eventBus = eventBus
    this.kafkaTemplate = kafkaTemplate

    this.eventBus.register(this)
  }

  @Handler
  void onAddressCreatedEvent(AddressCreatedEvent event) {
    publish(ADDRESS_CREATED_TOPIC, event)
  }

  @Handler
  void onAddressUpdatedEvent(AddressUpdatedEvent event) {
    publish(ADDRESS_UPDATED_TOPIC, event)
  }

  @Handler
  void onAddressDeletedEvent(AddressDeletedEvent event) {
    publish(ADDRESS_DELETED_TOPIC, event)
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
