package com.example.sakila.module.city

import com.example.sakila.event.bus.EventBus
import com.example.sakila.exception.NotFoundException
import com.example.sakila.module.city.repository.CityRepository
import spock.lang.Specification

class CityServiceTest extends Specification {

  private final EventBus eventBus = new EventBus('city-test-event-bus')

  private final CityRepository cityRepository = Mock(CityRepository)

  private final CityService cityService = new CityService(eventBus, cityRepository)

  void 'getCityById - should return null when given id is null'() {
    when:
    City city = cityService.getCityById(null)

    then:
    city == null
  }

  void 'getCityByAddressId - should return null when given id is null'() {
    when:
    City city = cityService.getCityByAddressId(null)

    then:
    city == null
  }

  void 'getCitiesByCountryId - should return null when given id is null'() {
    when:
    List<City> cities = cityService.getCitiesByCountry(null)

    then:
    cities == null
  }

  void 'updateCity - should not throw exceptions'() {
    given:
    final long existingCityId = 1l
    cityRepository.getCityById(existingCityId) >> new City()
    cityRepository.updateCity(_) >> new City()

    when:
    cityService.updateCity(existingCityId, new City())

    then:
    noExceptionThrown()
  }

  void 'updateCity - should throw NotFoundException when there is no city for the given id'() {
    given:
    final long nonExistingCityId = -1L

    when:
    cityService.updateCity(nonExistingCityId, new City())

    then:
    thrown NotFoundException
  }

  void 'deleteCity - should throw NotFoundException when there is no city for the given id'() {
    given:
    final long nonExistingCityId = -1L

    when:
    cityService.deleteCity(nonExistingCityId)

    then:
    thrown NotFoundException
  }
}
