package com.example.sakila.module.film.event;

import com.example.sakila.event.bus.EventBus;
import com.example.sakila.event.bus.Handler;
import com.example.sakila.module.film.event.model.FilmCreatedEvent;
import com.example.sakila.module.film.event.model.FilmDeletedEvent;
import com.example.sakila.module.film.event.model.FilmUpdatedEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class FilmEventPublisher {

  private static final String FILM_CREATED_TOPIC = "sakila-film-film-created";

  private static final String FILM_UPDATED_TOPIC = "sakila-film-film-updated";

  private static final String FILM_DELETED_TOPIC = "sakila-film-film-deleted";

  private final ObjectMapper objectMapper = new ObjectMapper();

  private final EventBus eventBus;

  private final KafkaTemplate<String, String> kafkaTemplate;

  @Autowired
  public FilmEventPublisher(@Qualifier("FilmEventBus") EventBus eventBus, KafkaTemplate kafkaTemplate) {
    this.eventBus = eventBus;
    this.kafkaTemplate = kafkaTemplate;

    this.eventBus.register(this);
  }

  @Handler
  public void onFilmCreatedEvent(FilmCreatedEvent event) {
    String serializedMessage = serialize(event);
    kafkaTemplate.send(FILM_CREATED_TOPIC, serializedMessage);
  }

  @Handler
  public void onFilmUpdatedEvent(FilmUpdatedEvent event) {
    String serializedMessage = serialize(event);
    kafkaTemplate.send(FILM_UPDATED_TOPIC, serializedMessage);
  }

  @Handler
  public void onFilmDeletedEvent(FilmDeletedEvent event) {
    String serializedMessage = serialize(event);
    kafkaTemplate.send(FILM_DELETED_TOPIC, serializedMessage);
  }

  private String serialize(Object object) {
    try {
      return objectMapper.writeValueAsString(object);
    } catch(JsonProcessingException e) {
      throw new IllegalArgumentException(e);
    }
  }
}
