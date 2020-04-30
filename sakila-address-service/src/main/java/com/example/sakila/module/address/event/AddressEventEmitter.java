package com.example.sakila.module.address.event;

import com.example.sakila.event.bus.EventBus;
import com.example.sakila.event.bus.Handler;
import com.example.sakila.module.address.event.model.AddressCreatedEvent;
import com.example.sakila.module.address.event.model.AddressDeletedEvent;
import com.example.sakila.module.address.event.model.AddressUpdatedEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class AddressEventEmitter {

  private static final String ADDRESS_CREATED_TOPIC = "sakila-address-address-created";

  private static final String ADDRESS_UPDATED_TOPIC = "sakila-address-address-updated";

  private static final String ADDRESS_DELETED_TOPIC = "sakila-address-address-deleted";

  private final ObjectMapper objectMapper = new ObjectMapper();

  private final EventBus eventBus;

  private final KafkaTemplate<String, String> kafkaTemplate;

  @Autowired
  public AddressEventEmitter(@Qualifier("AddressEventBUs") EventBus eventBus, KafkaTemplate kafkaTemplate) {
    this.eventBus = eventBus;
    this.kafkaTemplate = kafkaTemplate;

    this.eventBus.register(this);
  }

  @Handler
  public void onAddressCreatedEvent(AddressCreatedEvent event) {
    String serializedMessage = serialize(event);
    kafkaTemplate.send(ADDRESS_CREATED_TOPIC, serializedMessage);
  }

  @Handler
  public void onAddressUpdatedEvent(AddressUpdatedEvent event) {
    String serializedMatcher = serialize(event);
    kafkaTemplate.send(ADDRESS_UPDATED_TOPIC, serializedMatcher);
  }

  @Handler
  public void onAddressDeletedEvent(AddressDeletedEvent event) {
    String serializedMessage = serialize(event);
    kafkaTemplate.send(ADDRESS_DELETED_TOPIC, serializedMessage);
  }

  private String serialize(Object object) {
    try {
      return objectMapper.writeValueAsString(object);
    } catch (JsonProcessingException e) {
      throw new IllegalArgumentException(e);
    }
  }
}
