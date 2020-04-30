package com.example.sakila.module.country.event;

import com.example.sakila.module.country.Country;
import com.example.sakila.module.country.event.model.CountryEventDTO;

public class CountryEventUtils {

  public static CountryEventDTO toDto(Country country) {
    CountryEventDTO dto = new CountryEventDTO();

    dto.setId(country.getId());
    dto.setCountry(country.getCountry());
    dto.setLastUpdate(country.getLastUpdate());

    return dto;
  }
}
