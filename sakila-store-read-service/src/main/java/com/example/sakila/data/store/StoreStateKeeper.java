package com.example.sakila.data.store;

import com.example.sakila.data.event.ProcessedEventService;
import com.example.sakila.module.store.Store;
import com.example.sakila.module.store.StoreService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class StoreStateKeeper {

  private static final String GROUP_ID = "sakila_store_read_service";

  private static final String WRITE_TOPIC = "sakila-store-write-store-dto-stream";

  private static final String DELETE_TOPIC = "sakila-store-write-store-delete-stream";

  private final Logger log = LoggerFactory.getLogger(StoreStateKeeper.class);

  private final ObjectMapper objectMapper;

  private final StoreService storeService;

  private final ProcessedEventService eventService;

  @Autowired
  public StoreStateKeeper(StoreService storeService, ProcessedEventService eventService, ObjectMapper objectMapper) {
    this.storeService = storeService;
    this.eventService = eventService;
    this.objectMapper = objectMapper;
  }

  @KafkaListener(topics = {WRITE_TOPIC}, groupId = GROUP_ID, concurrency = "1")
  public void consumeStoreUpdatedEventStream(String message) {
    log.info("Consuming message from {}: {}", WRITE_TOPIC, message);
    StoreEventMessage eventMessage = deserialize(message, StoreEventMessage.class);
    if (isEventInvalidForProcessing(eventMessage)) return;

    Store store = fromDTO(eventMessage.getStoreDTO());

    if (storeService.getStoreById(store.getId()) != null) {
      storeService.updateStore(store.getId(), store);
    } else {
      storeService.createStore(store);
    }

    eventService.markEventAsProcessed(eventMessage.getEventId(), store.getId(), eventMessage.getStoreVersion());
  }

  @KafkaListener(topics = {DELETE_TOPIC}, groupId = GROUP_ID)
  public void consumeStoreDeletedEventStream(String message) {
    log.info("Consuming message from {}: {}", DELETE_TOPIC, message);
    StoreDeletedEvent deletedEvent = deserialize(message, StoreDeletedEvent.class);

    storeService.deleteStore(deletedEvent.getStoreId());
  }

  private boolean isEventInvalidForProcessing(StoreEventMessage eventMessage) {
    if (eventService.isEventProcessed(eventMessage.getEventId())) return true;

    Long currentVersion = eventService.getAggregateVersion(eventMessage.getStoreDTO().getId());

    return eventMessage.getStoreVersion() != (currentVersion + 1);
  }

  private <T> T deserialize(String json, Class<T> type) {
    try {
      return objectMapper.readValue(json, type);
    } catch (JsonProcessingException e) {
      throw new IllegalArgumentException(String.format("Failed parsing json: %s", json), e);
    }
  }

  private Store fromDTO(StoreDTO dto) {
    Store store = new Store();

    store.setId(dto.getId());
    store.setAddressId(dto.getAddressId());
    store.setManagerStaffId(dto.getManagerStaffId());

    return store;
  }
}
