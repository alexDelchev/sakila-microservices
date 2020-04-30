package com.example.sakila.module.city.event.model;

public class CityDeletedEvent {

  private Long cityId;

  public Long getCityId() {
    return cityId;
  }

  public void setCityId(Long cityId) {
    this.cityId = cityId;
  }
}
