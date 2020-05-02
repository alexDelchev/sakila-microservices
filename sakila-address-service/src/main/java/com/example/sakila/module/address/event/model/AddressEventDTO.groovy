package com.example.sakila.module.address.event.model

import com.example.sakila.module.city.event.model.CityEventDTO

class AddressEventDTO {

  Long id

  String address

  String address2

  String district

  CityEventDTO city

  String postalCode

  String phone

  Date lastUpdate

}
