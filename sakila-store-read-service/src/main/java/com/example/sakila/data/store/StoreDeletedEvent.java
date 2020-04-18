package com.example.sakila.data.store;

public class StoreDeletedEvent {

  private Long storeId;

  public Long getStoreId() {
    return storeId;
  }

  public void setStoreId(Long storeId) {
    this.storeId = storeId;
  }
}
