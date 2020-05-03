package com.example.sakila.module.country

import com.example.sakila.event.bus.EventBus
import com.example.sakila.exception.NotFoundException
import com.example.sakila.module.country.repository.CountryRepository
import spock.lang.Specification

class CountryServiceTest extends Specification {

  private final EventBus eventBus = new EventBus('country-test-event-bus')

  private final CountryRepository countryRepository = Mock(CountryRepository)

  private final CountryService countryService = new CountryService(eventBus, countryRepository)

  void 'getCountryById - should return null when given id is null'() {
    when:
    Country country = countryService.getCountryById(null)

    then:
    country == null
  }

  void 'getCountryByAddressId - should return null when given id is null'() {
    when:
    Country country = countryService.getCountryByAddressId(null)

    then:
    country == null
  }

  void 'updateCountry - should not throw exceptions'() {
    given:
    final long existingCountryId = 1L
    countryRepository.getCountryById(existingCountryId) >> new Country()
    countryRepository.updateCountry(_) >> new Country()

    when:
    countryService.updateCountry(existingCountryId, new Country())

    then:
    noExceptionThrown()
  }

  void 'updateCountry - should throw NotFoundException when there is no entity for the given id'() {
    given:
    final long nonExistingCountryId = 1L

    when:
    countryService.updateCountry(nonExistingCountryId, new Country())

    then:
    thrown NotFoundException
  }

  void 'deleteCountry - should throw NotFoundException when there is no entity for the given id'() {
    given:
    final long nonExistingCountryId = -1L

    when:
    countryService.deleteCountry(nonExistingCountryId)

    then:
    thrown NotFoundException
  }

}
