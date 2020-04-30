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

@Component
public class ActorEventEmitter {

  private static final String ACTOR_CREATED_TOPIC = "sakila-film-actor-created";

  private static final String ACTOR_UPDATED_TOPIC = "sakila-film-actor-updated";

  private static final String ACTOR_DELETED_TOPIC = "sakila-film-actor-deleted";

  private final ObjectMapper objectMapper = new ObjectMapper();

  private final EventBus eventBus;

  private final KafkaTemplate<String, String> kafkaTemplate;

  public ActorEventEmitter(@Qualifier("ActorEventBus") EventBus eventBus, KafkaTemplate kafkaTemplate) {
    this.eventBus = eventBus;
    this.kafkaTemplate = kafkaTemplate;

    this.eventBus.register(this);
  }

  @Handler
  public void onActorCreatedEvent(ActorCreatedEvent event) {
    String serializedMessage = serialize(event);
    kafkaTemplate.send(ACTOR_CREATED_TOPIC, serializedMessage);
  }

  @Handler
  public void onActorUpdatedEvent(ActorUpdatedEvent event) {
    String serializedMessage = serialize(event);
    kafkaTemplate.send(ACTOR_UPDATED_TOPIC, serializedMessage);
  }

  @Handler
  public void onActorDeletedEvent(ActorDeletedEvent event) {
    String serializedMessage = serialize(event);
    kafkaTemplate.send(ACTOR_DELETED_TOPIC, serializedMessage);
  }

  private String serialize(Object object) {
    try {
      return objectMapper.writeValueAsString(object);
    } catch(JsonProcessingException e) {
      throw new IllegalArgumentException(e);
    }
  }
}
