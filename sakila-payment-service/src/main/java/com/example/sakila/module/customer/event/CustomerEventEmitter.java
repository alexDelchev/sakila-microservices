package com.example.sakila.module.customer.event;

import com.example.sakila.event.bus.EventBus;
import com.example.sakila.event.bus.Handler;
import com.example.sakila.module.customer.event.model.CustomerCreatedEvent;
import com.example.sakila.module.customer.event.model.CustomerDeletedEvent;
import com.example.sakila.module.customer.event.model.CustomerUpdatedEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class CustomerEventEmitter {

  private static final String CUSTOMER_CREATED_TOPIC = "sakila-payment-customer-created";

  private static final String CUSTOMER_UPDATED_TOPIC = "sakila-payment-customer-updated";

  private static final String CUSTOMER_DELETED_TOPIC = "sakila-payment-customer-deleted";

  private final ObjectMapper objectMapper = new ObjectMapper();

  private final EventBus eventBus;

  private final KafkaTemplate<String, String> kafkaTemplate;

  @Autowired
  public CustomerEventEmitter(@Qualifier("CustomerEventBus") EventBus eventBus, KafkaTemplate kafkaTemplate) {
    this.eventBus = eventBus;
    this.kafkaTemplate = kafkaTemplate;

    this.eventBus.register(this);
  }

  @Handler
  public void onCustomerCreatedEvent(CustomerCreatedEvent event) {
    String serializedMessage = serialize(event);
    kafkaTemplate.send(CUSTOMER_CREATED_TOPIC, serializedMessage);
  }

  @Handler
  public void onCustomerUpatedEvent(CustomerUpdatedEvent event) {
    String serializedMessage = serialize(event);
    kafkaTemplate.send(CUSTOMER_UPDATED_TOPIC, serializedMessage);
  }

  @Handler
  public void onCustomerDeletedEvent(CustomerDeletedEvent event) {
    String serializedMessage = serialize(event);
    kafkaTemplate.send(CUSTOMER_DELETED_TOPIC, serializedMessage);
  }

  private String serialize(Object object) {
    try {
      return objectMapper.writeValueAsString(object);
    } catch (JsonProcessingException e) {
      throw new IllegalArgumentException(String.format("Failed parsing json: %s", e.getMessage()), e);
    }
  }
}
