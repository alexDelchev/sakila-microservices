package com.example.sakila.module.rental.event.model;

public class RentalCreatedEvent {

  private RentalEventDTO dto;

  public RentalEventDTO getDto() {
    return dto;
  }

  public void setDto(RentalEventDTO dto) {
    this.dto = dto;
  }
}
