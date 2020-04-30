package com.example.sakila.module.film.event.model;

public class InventoryEventDTO {

  private Long storeId;

  private Integer quantity;

  public Long getStoreId() {
    return storeId;
  }

  public void setStoreId(Long storeId) {
    this.storeId = storeId;
  }

  public Integer getQuantity() {
    return quantity;
  }

  public void setQuantity(Integer quantity) {
    this.quantity = quantity;
  }
}
