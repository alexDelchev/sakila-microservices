package com.example.sakila.module.customer.event.model;

public class CustomerDeletedEvent {

  private Long customerId;

  public Long getCustomerId() {
    return customerId;
  }

  public void setCustomerId(Long customerId) {
    this.customerId = customerId;
  }
}
