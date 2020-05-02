package com.example.sakila.module.address.event

import com.example.sakila.module.address.Address
import com.example.sakila.module.address.event.model.AddressEventDTO
import com.example.sakila.module.city.event.CityEventUtils

class AddressEventUtils {

  static AddressEventDTO toDto(Address address) {
    new AddressEventDTO(
        id: address.id,
        address: address.address,
        address2: address.address2,
        district: address.district,
        postalCode: address.postalCode,
        phone: address.phone,
        lastUpdate: address.lastUpdate,
        city: address.city ? CityEventUtils.toDto(address.city) : null
    )
  }
}
