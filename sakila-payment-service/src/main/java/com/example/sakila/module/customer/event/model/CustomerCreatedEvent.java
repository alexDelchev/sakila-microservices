package com.example.sakila.module.customer.event.model;

public class CustomerCreatedEvent {

  private CustomerEventDTO dto;

  public CustomerEventDTO getDto() {
    return dto;
  }

  public void setDto(CustomerEventDTO dto) {
    this.dto = dto;
  }
}
