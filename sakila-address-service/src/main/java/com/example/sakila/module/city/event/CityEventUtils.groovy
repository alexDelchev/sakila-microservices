package com.example.sakila.module.city.event

import com.example.sakila.module.city.City
import com.example.sakila.module.city.event.model.CityEventDTO
import com.example.sakila.module.country.event.CountryEventUtils

class CityEventUtils {

  static CityEventDTO toDto(City city) {
    new CityEventDTO(
        id: city.id,
        city: city.city,
        country: city.country ? CountryEventUtils.toDto(city.country) : null,
        lastUpdate: city.lastUpdate
    )
  }
  
}
