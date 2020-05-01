package com.example.sakila.event.external.rental.model;

public class RentalUpdatedEvent {

  private RentalEventDTO dto;

  public RentalEventDTO getDto() {
    return dto;
  }

  public void setDto(RentalEventDTO dto) {
    this.dto = dto;
  }
}
