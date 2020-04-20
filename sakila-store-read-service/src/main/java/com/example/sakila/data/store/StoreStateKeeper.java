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

  @KafkaListener(topics = {CREATE_TOPIC}, groupId = GROUP_ID)
  public void consumeStoreCreatedEventStream(String message) {
    StoreEventMessage eventMessage = deserialize(message, StoreEventMessage.class);
    if (eventService.isEventProcessed(eventMessage.getEventId())) return;

    Store store = fromDTO(eventMessage.getStoreDTO());
    storeService.createStore(store);

    eventService.markEventAsProcessed(eventMessage.getEventId());
  }

  @KafkaListener(topics = {UPDATE_TOPIC}, groupId = GROUP_ID)
  public void consumeStoreUpdatedEventStream(String message) {
    StoreEventMessage eventMessage = deserialize(message, StoreEventMessage.class);
    if (eventService.isEventProcessed(eventMessage.getEventId())) return;

    Store store = fromDTO(eventMessage.getStoreDTO());
    storeService.updateStore(store.getId(), store);

    eventService.markEventAsProcessed(eventMessage.getEventId());
  }

  @KafkaListener(topics = {DELETE_TOPIC}, groupId = GROUP_ID)
  public void consumeStoreDeletedEventStream(String message) {
    StoreDeletedEvent deletedEvent = deserialize(message, StoreDeletedEvent.class);

    storeService.deleteStore(deletedEvent.getStoreId());
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
