package com.example.sakila.module.country;

import com.example.sakila.event.bus.EventBus;
import com.example.sakila.exception.DataConflictException;
import com.example.sakila.exception.NotFoundException;
import com.example.sakila.module.country.event.CountryEventUtils;
import com.example.sakila.module.country.event.model.CountryCreatedEvent;
import com.example.sakila.module.country.event.model.CountryEventDTO;
import com.example.sakila.module.country.repository.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CountryService {

  private final EventBus eventBus;

  private final CountryRepository countryRepository;

  @Autowired
  private CountryService(@Qualifier("CountryEventBus") EventBus eventBus, CountryRepository countryRepository) {
    this.eventBus = eventBus;
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

  public void generateCreatedEvent(Country country) {
    CountryEventDTO dto = CountryEventUtils.toDto(country);
    CountryCreatedEvent event = new CountryCreatedEvent();
    event.setDto(dto);
    eventBus.emit(event);
  }

  public Country createCountry(Country country) {
    Country result = countryRepository.insertCountry(country);

    generateCreatedEvent(result);

    return result;
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
