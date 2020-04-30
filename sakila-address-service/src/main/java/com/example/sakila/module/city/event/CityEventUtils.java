package com.example.sakila.module.city.event;

import com.example.sakila.module.city.City;
import com.example.sakila.module.city.event.model.CityEventDTO;
import com.example.sakila.module.country.event.CountryEventUtils;

public class CityEventUtils {

  public static CityEventDTO toDto(City city) {
    CityEventDTO dto = new CityEventDTO();

    dto.setId(city.getId());
    dto.setCity(city.getCity());
    if (city.getCountry() != null) dto.setCountry(CountryEventUtils.toDto(city.getCountry()));
    dto.setLastUpdate(city.getLastUpdate());

    return dto;
  }
}
