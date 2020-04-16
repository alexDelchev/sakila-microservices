package com.example.sakila.module.store.command.model;

public abstract class BasicStoreCommand<T> {

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
