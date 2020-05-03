package com.example.sakila.module.city

import com.example.sakila.exception.NotFoundException
import com.example.sakila.generated.server.model.CityDTO
import com.example.sakila.module.country.CountryService
import spock.lang.Specification

class CityControllerTest extends Specification {

  private final CountryService countryService = Mock(CountryService)

  private final CityService cityService = Mock(CityService)

  private final CityController cityController = new CityController(cityService, countryService)

  def 'createCity - should throw NotFoundException when given non existing country id'() {
    given:
    final long nonExistingCountryId = -1L
    CityDTO invalidDto = new CityDTO(countryId: nonExistingCountryId)

    when:
    cityController.createCity(invalidDto)

    then:
    thrown NotFoundException
  }

  def 'replaceCity - should throw NotFoundException when given non existing country id'() {
    given:
    final long updatedCityId = 1L
    final long nonExistingCountryId = -1L
    CityDTO invalidDto = new CityDTO(countryId: nonExistingCountryId)

    when:
    cityController.replaceCity(updatedCityId, invalidDto)

    then:
    thrown NotFoundException
  }
}
