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

import java.util.UUID;

@Service
public class StoreEventService {

  private static final String WRITE_TOPIC = "sakila-store-write-store-dto-stream";

  private static final String DELETE_TOPIC = "sakila-store-write-store-delete-stream";

  private final EventBus eventBus;

  private final StoreService storeService;

  private final KafkaTemplate<String, String> kafkaTemplate;

  private final ObjectMapper mapper;

  @Autowired
  public StoreEventService(
      @Qualifier("StoreEventBus") EventBus eventBus,
      StoreService storeService,
      KafkaTemplate<String, String> kafkaTemplate,
      ObjectMapper objectMapper
  ) {
    this.eventBus = eventBus;
    this.storeService = storeService;
    this.kafkaTemplate = kafkaTemplate;
    this.mapper = objectMapper;

    this.eventBus.register(this);
  }

  @Handler
  public void onStoreCreatedEvent(StoreCreatedEvent event) {
    String json = generateEventMessageJson(event.getId(), event.getStoreId(), event.getVersion());
    kafkaTemplate.send(WRITE_TOPIC, json);
  }

  @Handler
  public void onStoreDeletedEvent(StoreDeletedEvent event) {
    String json = toJson(event);
    kafkaTemplate.send(DELETE_TOPIC, json);
  }

  @Handler
  public void onManagerChangedEvent(ManagerChangedEvent event) {
    String json = generateEventMessageJson(event.getId(), event.getStoreId(), event.getVersion());
    kafkaTemplate.send(WRITE_TOPIC, json);
  }

  @Handler
  public void onAddressChangedEvent(AddressChangedEvent event) {
    String json = generateEventMessageJson(event.getId(), event.getStoreId(), event.getVersion());
    kafkaTemplate.send(WRITE_TOPIC, json);
  }

  private String generateEventMessageJson(UUID eventId, Long storeId, Long storeVersion) {
    StoreEventMessage eventMessage = generateEventMessage(eventId, storeId, storeVersion);

    return toJson(eventMessage);
  }

  private StoreEventMessage generateEventMessage(UUID eventId, Long storeId, Long storeVersion) {
    StoreDTO dto = getStoreDTO(storeId, eventId);

    StoreEventMessage eventMessage = new StoreEventMessage();

    eventMessage.setEventId(eventId);
    eventMessage.setStoreDTO(dto);
    eventMessage.setStoreVersion(storeVersion);

    return eventMessage;
  }

  private StoreDTO getStoreDTO(Long storeId, UUID eventId) {
    StoreWriteModel store = storeService.getStoreAtEvent(storeId, eventId);
    return StoreUtils.toDTO(store);
  }

  private String toJson(Object object) {
    try {
      return mapper.writeValueAsString(object);
    } catch (JsonProcessingException e) {
      throw new IllegalArgumentException("Failed to serialise to JSON", e);
    }
  }
}
