package com.example.sakila.data.store;

import com.example.sakila.data.event.ProcessedEventService;
import com.example.sakila.module.store.Store;
import com.example.sakila.module.store.StoreService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class StoreStateKeeper {

  private static final String GROUP_ID = "sakila_store_read_service";

  private static final String CREATE_TOPIC = "store-create-event-stream";

  private static final String UPDATE_TOPIC = "store-dto-stream";

  private static final String DELETE_TOPIC = "store-delete-event-stream";

  private final ObjectMapper objectMapper = new ObjectMapper();

  private final StoreService storeService;

  private final ProcessedEventService eventService;

  @Autowired
  public StoreStateKeeper(StoreService storeService, ProcessedEventService eventService) {
    this.storeService = storeService;
    this.eventService = eventService;
  }

}
