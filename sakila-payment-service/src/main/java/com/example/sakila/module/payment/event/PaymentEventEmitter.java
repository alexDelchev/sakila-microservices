package com.example.sakila.module.payment.event;

import com.example.sakila.event.bus.EventBus;
import com.example.sakila.event.bus.Handler;
import com.example.sakila.module.payment.event.model.PaymentCreatedEvent;
import com.example.sakila.module.payment.event.model.PaymentDeletedEvent;
import com.example.sakila.module.payment.event.model.PaymentUpdatedEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class PaymentEventEmitter {

  private static final String PAYMENT_CREATE_TOPIC = "sakila-payment-payment-create";

  private static final String PAYMENT_UPDATE_TOPIC = "sakila-payment-payment-update";

  private static final String PAYMENT_DELETE_TOPIC = "sakila-payment-payment-delete";

  private final ObjectMapper objectMapper = new ObjectMapper();

  private final EventBus eventBus;

  private final KafkaTemplate<String, String> kafkaTemplate;

  @Autowired
  public PaymentEventEmitter(@Qualifier("PaymentEventBus") EventBus eventBus, KafkaTemplate kafkaTemplate) {
    this.eventBus = eventBus;
    this.kafkaTemplate = kafkaTemplate;

    this.eventBus.register(this);
  }

  @Handler
  public void onPaymentCreatedEvent(PaymentCreatedEvent event) {
    String serializedMessage = serialize(event);
    kafkaTemplate.send(PAYMENT_CREATE_TOPIC, serializedMessage);
  }

  @Handler
  public void onPaymentUpdatedEvent(PaymentUpdatedEvent event) {
    String seriliazedMessage = serialize(event);
    kafkaTemplate.send(PAYMENT_UPDATE_TOPIC, seriliazedMessage);
  }

  @Handler
  public void onPaymentDeletedEvent(PaymentDeletedEvent event) {
    String serializedMessage = serialize(event);
    kafkaTemplate.send(PAYMENT_DELETE_TOPIC, serializedMessage);
  }

  private String serialize(Object object) {
    try {
      return objectMapper.writeValueAsString(object);
    } catch (JsonProcessingException e) {
      throw new IllegalArgumentException(String.format("Failed parsing json: %s", e.getMessage()), e);
    }
  }
}
