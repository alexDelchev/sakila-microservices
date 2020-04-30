package com.example.sakila.module.country.event.model;

public class CountryCreatedEvent {

  private CountryEventDTO dto;

  public CountryEventDTO getDto() {
    return dto;
  }

  public void setDto(CountryEventDTO dto) {
    this.dto = dto;
  }
}
