package com.example.sakila.module.country.event.model;

public class CountryDeletedEvent {

  private Long countryId;

  public Long getCountryId() {
    return countryId;
  }

  public void setCountryId(Long countryId) {
    this.countryId = countryId;
  }
}
