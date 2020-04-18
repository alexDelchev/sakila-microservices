package com.example.sakila.module.store.event;

import com.example.sakila.module.store.StoreDTO;

import java.util.UUID;

public class StoreEventMessage {

  private UUID eventId;

  private StoreDTO storeDTO;

  public UUID getEventId() {
    return eventId;
  }

  public void setEventId(UUID eventId) {
    this.eventId = eventId;
  }

  public StoreDTO getStoreDTO() {
    return storeDTO;
  }

  public void setStoreDTO(StoreDTO storeDTO) {
    this.storeDTO = storeDTO;
  }
}
