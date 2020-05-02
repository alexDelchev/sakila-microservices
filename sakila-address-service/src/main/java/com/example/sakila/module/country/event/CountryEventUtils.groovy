package com.example.sakila.module.country.event

import com.example.sakila.module.country.Country
import com.example.sakila.module.country.event.model.CountryEventDTO

class CountryEventUtils {

  static CountryEventDTO toDto(Country country) {
    new CountryEventDTO(
        id: country.id,
        country: country.country,
        lastUpdate: country.getLastUpdate()
    )
  }
}
