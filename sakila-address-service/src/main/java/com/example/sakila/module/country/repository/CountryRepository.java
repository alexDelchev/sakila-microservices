package com.example.sakila.module.country.repository;

import com.example.sakila.module.country.Country;

import java.util.List;

public interface CountryRepository {

  Country getCountryById(Long id);

  Country getCountryByAddressId(Long addressId);

  List<Country> getAllCountries();

  Country insertCountry(Country country);

  Country updateCountry(Country country);

  void deleteCountry(Long id);
}
