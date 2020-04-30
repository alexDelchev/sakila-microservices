package com.example.sakila.module.city.event.model;

import com.example.sakila.module.country.event.model.CountryEventDTO;

import java.util.Date;

public class CityEventDTO {

  private Long id;

  private String city;

  private CountryEventDTO country;

  private Date lastUpdate;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public CountryEventDTO getCountry() {
    return country;
  }

  public void setCountry(CountryEventDTO country) {
    this.country = country;
  }

  public Date getLastUpdate() {
    return lastUpdate;
  }

  public void setLastUpdate(Date lastUpdate) {
    this.lastUpdate = lastUpdate;
  }
}
