package com.example.sakila.module.address.event.model;

public class AddressUpdatedEvent {

  private AddressEventDTO dto;

  public AddressEventDTO getDto() {
    return dto;
  }

  public void setDto(AddressEventDTO dto) {
    this.dto = dto;
  }
}
