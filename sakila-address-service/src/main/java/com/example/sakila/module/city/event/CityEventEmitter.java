package com.example.sakila.module.city.event;

import com.example.sakila.event.bus.EventBus;
import com.example.sakila.event.bus.Handler;
import com.example.sakila.module.city.event.model.CityCreatedEvent;
import com.example.sakila.module.city.event.model.CityDeletedEvent;
import com.example.sakila.module.city.event.model.CityUpdatedEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class CityEventEmitter {

  private static final String CITY_CREATED_TOPIC = "sakila-address-city-created";

  private static final String CITY_UPDATED_TOPIC = "sakila-address-city-updated";

  private static final String CITY_DELETED_TOPIC = "sakila-address-city-deleted";

  private final ObjectMapper objectMapper = new ObjectMapper();

  private final EventBus eventBus;

  private final KafkaTemplate<String, String> kafkaTemplate;

  @Autowired
  public CityEventEmitter(@Qualifier("CityEventBus") EventBus eventBus, KafkaTemplate kafkaTemplate) {
    this.eventBus = eventBus;
    this.kafkaTemplate = kafkaTemplate;

    this.eventBus.register(this);
  }

  @Handler
  public void onCityCreatedEvent(CityCreatedEvent event) {
    String serializedMessage = serialize(event);
    kafkaTemplate.send(CITY_CREATED_TOPIC, serializedMessage);
  }

  @Handler
  public void onCityUpdatedEvent(CityUpdatedEvent event) {
    String serializedMessage = serialize(event);
    kafkaTemplate.send(CITY_UPDATED_TOPIC, serializedMessage);
  }

  @Handler
  public void onCityDeletedEvent(CityDeletedEvent event) {
    String serializedMessage = serialize(event);
    kafkaTemplate.send(CITY_DELETED_TOPIC, serializedMessage);
  }

  private String serialize(Object object) {
    try {
      return objectMapper.writeValueAsString(object);
    } catch (JsonProcessingException e) {
      throw new IllegalArgumentException(e);
    }
  }
}
