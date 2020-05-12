package com.example.sakila.event.external.rental;

import com.example.sakila.event.external.rental.model.RentalCreatedEvent;
import com.example.sakila.event.external.rental.model.RentalEventDTO;
import com.example.sakila.module.film.FilmService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class RentalEventStreamsConsumer {

  private final Logger log = LoggerFactory.getLogger(RentalEventStreamsConsumer.class);

  private static final String GROUP_ID = "sakila-film-rental-consumer";

  private static final String RENTAL_CREATED_TOPIC = "sakila-payment-rental-created";

  private final ObjectMapper objectMapper = new ObjectMapper();

  private final FilmService filmService;

  @Autowired
  public RentalEventStreamsConsumer(FilmService filmService) {
    this.filmService = filmService;
  }

  @KafkaListener(topics = {RENTAL_CREATED_TOPIC}, groupId = GROUP_ID)
  public void onRentalCreated(String message) {
    log.info("Consuming RentalCreatedEvent: {}", message);
    RentalCreatedEvent event = deserialize(message, RentalCreatedEvent.class);
    RentalEventDTO dto = event.getDto();
    filmService.decreaseQuantityForStore(dto.getFilmId(),dto.getStoreId());
  }

  private <T> T deserialize(String json, Class<T> type) {
    try {
      return objectMapper.readValue(json, type);
    } catch(JsonProcessingException e) {
      throw new IllegalArgumentException(e);
    }
  }
}
