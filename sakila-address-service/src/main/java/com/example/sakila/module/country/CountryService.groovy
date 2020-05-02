package com.example.sakila.module.country

import com.example.sakila.event.bus.EventBus
import com.example.sakila.exception.DataConflictException
import com.example.sakila.exception.NotFoundException
import com.example.sakila.module.country.event.CountryEventUtils
import com.example.sakila.module.country.event.model.CountryCreatedEvent
import com.example.sakila.module.country.event.model.CountryDeletedEvent
import com.example.sakila.module.country.event.model.CountryEventDTO
import com.example.sakila.module.country.event.model.CountryUpdatedEvent
import com.example.sakila.module.country.repository.CountryRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.stereotype.Service

@Service
class CountryService {

  private final EventBus eventBus

  private final CountryRepository countryRepository

  @Autowired
  private CountryService(@Qualifier("CountryEventBus") EventBus eventBus, CountryRepository countryRepository) {
    this.eventBus = eventBus
    this.countryRepository = countryRepository
  }

  Country getCountryById(Long id) {
    if (id == null) return null
    countryRepository.getCountryById(id)
  }

  Country getCountryByAddressId(Long addressId) {
    if (!addressId) return null
    countryRepository.getCountryByAddressId(addressId)
  }

  void generateCreatedEvent(Country country) {
    CountryEventDTO dto = CountryEventUtils.toDto(country)
    CountryCreatedEvent event = new CountryCreatedEvent()
    event.dto = dto
    eventBus.emit(event)
  }

  Country createCountry(Country country) {
    Country result = countryRepository.insertCountry(country)

    generateCreatedEvent(result)

    result
  }

  void generateUpdatedEvent(Country country) {
    CountryEventDTO dto = CountryEventUtils.toDto(country)
    CountryUpdatedEvent event = new CountryUpdatedEvent()
    event.dto = dto
    eventBus.emit(event)
  }

  Country updateCountry(Long id, Country source) {
    Country target = getCountryById(id)
    if (!target) throw new NotFoundException("Country for ID " + id + " does not exist")

    target.country = source.getCountry()

    Country result = countryRepository.updateCountry(target)

    generateUpdatedEvent(result)

    result
  }

  private void generateDeletedEvent(Long id) {
    CountryDeletedEvent event = new CountryDeletedEvent()
    event.countryId = id
    eventBus.emit(event)
  }

  void deleteCountry(Long id) {
    Country country = countryRepository.getCountryById(id)
    if (!country) throw new NotFoundException("Country for ID ${id} does not exist")

    try {
      countryRepository.deleteCountry(country)
      generateDeletedEvent(id)
    } catch (DataIntegrityViolationException e) {
      throw new DataConflictException(e.getMessage(),e)
    }
  }

  List<Country> getAllCountries() {
    countryRepository.getAllCountries()
  }
}
