package com.example.sakila.module.rental.event;


import com.example.sakila.event.bus.EventBus;
import com.example.sakila.event.bus.Handler;
import com.example.sakila.module.rental.event.model.RentalCreatedEvent;
import com.example.sakila.module.rental.event.model.RentalDeletedEvent;
import com.example.sakila.module.rental.event.model.RentalUpdatedEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;

public class RentalEventEmitter {

  private static final String RENTAL_CREATED_TOPIC = "sakila-payment-rental-created";

  private static final String RENTAL_UPDATED_TOPIC = "sakila-payment-rental-updated";

  private static final String RENTAL_DELETED_TOPIC = "sakila-payment-rental-deleted";

  private final ObjectMapper objectMapper = new ObjectMapper();

  private final EventBus eventBus;

  private final KafkaTemplate<String, String> kafkaTemplate;

  @Autowired
  public RentalEventEmitter(@Qualifier("RentalEventBus") EventBus eventBus, KafkaTemplate kafkaTemplate) {
    this.eventBus = eventBus;
    this.kafkaTemplate = kafkaTemplate;

    this.eventBus.register(this);
  }

  @Handler
  public void onRentalCreatedEvent(RentalCreatedEvent event) {
    String serializedMessage = serialize(event);
    kafkaTemplate.send(RENTAL_CREATED_TOPIC, serializedMessage);
  }

  @Handler
  public void onRentalUpdatedEvent(RentalUpdatedEvent event) {
    String serializedMessage = serialize(event);
    kafkaTemplate.send(RENTAL_UPDATED_TOPIC, serializedMessage);
  }

  @Handler
  public void onRentalDeletedEvent(RentalDeletedEvent event) {
    String serializedMessage = serialize(event);
    kafkaTemplate.send(RENTAL_DELETED_TOPIC, serializedMessage);
  }

  private String serialize(Object object) {
    try {
      return objectMapper.writeValueAsString(object);
    } catch (JsonProcessingException e) {
      throw new IllegalArgumentException(String.format("Failed parsing json: %s", e.getMessage()), e);
    }
  }
}
