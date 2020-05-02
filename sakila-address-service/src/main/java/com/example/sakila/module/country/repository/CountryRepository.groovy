package com.example.sakila.module.country.repository;

import com.example.sakila.module.country.Country;

interface CountryRepository {

  Country getCountryById(Long id);

  Country getCountryByAddressId(Long addressId);

  List<Country> getAllCountries();

  Country insertCountry(Country country);

  Country updateCountry(Country country);

  void deleteCountry(Country country);
}
