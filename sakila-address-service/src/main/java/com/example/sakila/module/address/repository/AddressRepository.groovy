package com.example.sakila.module.address.repository

import com.example.sakila.module.address.Address

interface AddressRepository {

  Address getAddressById(Long id)

  List<Address> getAddressesByCity(Long cityId)

  List<Address> getAddressesByCountry(Long countryId)

  Address insertAddress(Address address)

  Address updateAddress(Address address)

  void deleteAddress(Address address)
}
