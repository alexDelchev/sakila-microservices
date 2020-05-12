package com.example.sakila.module.actor.event;

import com.example.sakila.event.bus.EventBus;
import com.example.sakila.event.bus.Handler;
import com.example.sakila.module.actor.event.model.ActorCreatedEvent;
import com.example.sakila.module.actor.event.model.ActorDeletedEvent;
import com.example.sakila.module.actor.event.model.ActorUpdatedEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class ActorEventPublisher {

  private static final String ACTOR_CREATED_TOPIC = "sakila-film-actor-created";

  private static final String ACTOR_UPDATED_TOPIC = "sakila-film-actor-updated";

  private static final String ACTOR_DELETED_TOPIC = "sakila-film-actor-deleted";

  private final ObjectMapper objectMapper = new ObjectMapper();

  private final EventBus eventBus;

  private final KafkaTemplate<String, String> kafkaTemplate;

  public ActorEventPublisher(@Qualifier("ActorEventBus") EventBus eventBus, KafkaTemplate kafkaTemplate) {
    this.eventBus = eventBus;
    this.kafkaTemplate = kafkaTemplate;
  }

  @PostConstruct
  private void postConstruct() {
    eventBus.register(this);
  }

  @Handler
  public void onActorCreatedEvent(ActorCreatedEvent event) {
    publish(ACTOR_CREATED_TOPIC, event);
  }

  @Handler
  public void onActorUpdatedEvent(ActorUpdatedEvent event) {
    publish(ACTOR_UPDATED_TOPIC, event);
  }

  @Handler
  public void onActorDeletedEvent(ActorDeletedEvent event) {
    publish(ACTOR_DELETED_TOPIC, event);
  }

  private void publish(String topic, Object value) {
    String serializedMessage = serialize(value);
    kafkaTemplate.send(topic, serializedMessage);
  }

  private String serialize(Object object) {
    try {
      return objectMapper.writeValueAsString(object);
    } catch(JsonProcessingException e) {
      throw new IllegalArgumentException(e);
    }
  }
}
