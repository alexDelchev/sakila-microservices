package com.example.sakila.module.country;

import com.example.sakila.exception.DataConflictException;
import com.example.sakila.exception.NotFoundException;
import com.example.sakila.module.country.repository.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
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

  public Country updateCountry(Long id, Country source) {
    Country target = getCountryById(id);
    if (target == null) throw new NotFoundException("Country for ID " + id + " does not exist");

    target.setCountry(source.getCountry());

    return countryRepository.updateCountry(target);
  }

  public void deleteCountry(Long id) {
    Country country = countryRepository.getCountryById(id);
    if (country == null) throw new NotFoundException("Country for ID " + id + " does not exist");

    try {
      countryRepository.deleteCountry(country);
    } catch (DataIntegrityViolationException e) {
      throw new DataConflictException(e.getMessage(),e);
    }
  }

  public List<Country> getAllCountries() {
    return countryRepository.getAllCountries();
  }
}
