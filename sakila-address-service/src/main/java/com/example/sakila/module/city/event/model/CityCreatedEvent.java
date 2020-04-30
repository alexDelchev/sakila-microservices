package com.example.sakila.module.city.event.model;

public class CityCreatedEvent {

  private CityEventDTO cityEventDTO;

  public CityEventDTO getCityEventDTO() {
    return cityEventDTO;
  }

  public void setCityEventDTO(CityEventDTO cityEventDTO) {
    this.cityEventDTO = cityEventDTO;
  }
}
