package com.example.sakila.module.country;

import com.example.sakila.module.country.repository.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CountryService {

  private final CountryRepository countryRepository;

  @Autowired
  private CountryService(CountryRepository countryRepository) {
    this.countryRepository = countryRepository;
  }

  public Country getCountryById(Long id) {
    if (id == null) return null;
    return countryRepository.getCountryById(id);
  }

  public Country getCountryByAddressId(Long addressId) {
    if (addressId == null) return null;
    return countryRepository.getCountryByAddressId(addressId);
  }

  public Country addNewCountry(Country country) {
    return countryRepository.insertCountry(country);
  }

  public List<Country> getAllCountries() {
    return countryRepository.getAllCountries();
  }
}
