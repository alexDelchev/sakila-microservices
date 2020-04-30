package com.example.sakila.module.country.event.model;

public class CountryUpdatedEvent {

  private CountryEventDTO dto;

  public CountryEventDTO getDto() {
    return dto;
  }

  public void setDto(CountryEventDTO dto) {
    this.dto = dto;
  }
}
