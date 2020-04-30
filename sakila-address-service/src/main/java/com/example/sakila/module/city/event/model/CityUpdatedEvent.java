package com.example.sakila.module.city.event.model;

public class CityUpdatedEvent {

  private CityEventDTO dto;

  public CityEventDTO getDto() {
    return dto;
  }

  public void setDto(CityEventDTO dto) {
    this.dto = dto;
  }
}
