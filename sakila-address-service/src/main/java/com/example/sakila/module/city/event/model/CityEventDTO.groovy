package com.example.sakila.module.city.event.model

import com.example.sakila.module.country.event.model.CountryEventDTO

class CityEventDTO {

  Long id

  String city

  CountryEventDTO country

  Date lastUpdate
  
}
