package com.example.sakila.module.rental.event.model;

public class RentalUpdatedEvent {

  private RentalEventDTO dto;

  public RentalEventDTO getDto() {
    return dto;
  }

  public void setDto(RentalEventDTO dto) {
    this.dto = dto;
  }
}