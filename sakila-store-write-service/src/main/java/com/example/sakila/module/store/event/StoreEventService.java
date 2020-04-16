package com.example.sakila.module.store.event;

import com.example.sakila.event.bus.EventBus;
import com.example.sakila.event.bus.Handler;
import com.example.sakila.module.store.StoreDTO;
import com.example.sakila.module.store.StoreService;
import com.example.sakila.module.store.StoreUtils;
import com.example.sakila.module.store.StoreWriteModel;
import com.example.sakila.module.store.event.model.AddressChangedEvent;
import com.example.sakila.module.store.event.model.ManagerChangedEvent;
import com.example.sakila.module.store.event.model.StoreCreatedEvent;
import com.example.sakila.module.store.event.model.StoreDeletedEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class StoreEventService {

  private static final String DTO_TOPIC = "store-dto-stream";

  private static final String DELETE_TOPIC = "store-delete-event-stream";

  private final EventBus eventBus;

  private final StoreService storeService;

  private final KafkaTemplate<String, String> kafkaTemplate;

  private final ObjectMapper mapper = new ObjectMapper();

  @Autowired
  public StoreEventService(
      @Qualifier("StoreEventBus") EventBus eventBus,
      StoreService storeService,
      KafkaTemplate<String, String> kafkaTemplate
  ) {
    this.eventBus = eventBus;
    this.storeService = storeService;
    this.kafkaTemplate = kafkaTemplate;

    this.eventBus.register(this);
  }
  
}
