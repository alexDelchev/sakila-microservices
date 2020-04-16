package com.example.sakila.module.store.event.model;

import com.example.sakila.event.Event;
import com.example.sakila.module.store.StoreWriteModel;

public abstract class BasicStoreEvent<T> extends Event<StoreWriteModel> {

  private Long storeId;

  private T newValue;

  public Long getStoreId() {
    return storeId;
  }

  public void setStoreId(Long storeId) {
    this.storeId = storeId;
  }

  public T getNewValue() {
    return newValue;
  }

  public void setNewValue(T newValue) {
    this.newValue = newValue;
  }
}
