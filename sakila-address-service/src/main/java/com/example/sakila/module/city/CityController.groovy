package com.example.sakila.module.city

import com.example.sakila.exception.NotFoundException
import com.example.sakila.generated.server.api.CitiesApi
import com.example.sakila.generated.server.model.CityDTO
import com.example.sakila.module.country.Country
import com.example.sakila.module.country.CountryService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody

import java.time.OffsetDateTime
import java.time.ZoneId

@Controller
class CityController implements CitiesApi {

  private final CityService cityService

  private final CountryService countryService

  @Autowired
  CityController(CityService cityService, CountryService countryService) {
    this.cityService = cityService
    this.countryService = countryService
  }

  @Override
  ResponseEntity<List<CityDTO>> getAllCities() {
    ResponseEntity.ok(
        cityService.getAllCities().collect { toDTO(it) }
    )
  }

  @Override
  ResponseEntity<List<CityDTO>> getCitiesByCountryId(@PathVariable('id') Long id) {
    List<City> cities = cityService.getCitiesByCountry(id)
    ResponseEntity.ok(
        cities.collect{ toDTO(it) }
    )
  }

  @Override
  ResponseEntity<CityDTO> getCityById(@PathVariable('id') Long id) {
    City city = cityService.getCityById(id)
    ResponseEntity.ok(toDTO(city))
  }

  @Override
  ResponseEntity<CityDTO> createCity(@RequestBody CityDTO cityDTO) {
    City city = toEntity(cityDTO)
    ResponseEntity.ok(toDTO(cityService.createCity(city)))
  }

  @Override
  ResponseEntity<CityDTO> replaceCity(@PathVariable('id') Long id, @RequestBody CityDTO cityDTO) {
    City city = toEntity(cityDTO)
    ResponseEntity.ok(toDTO(cityService.updateCity(id, city)))
  }

  @Override
  ResponseEntity<Void> deleteCity(@PathVariable('id') Long id) {
    cityService.deleteCity(id)
    ResponseEntity.ok(null)
  }

  private CityDTO toDTO(City city) {
    new CityDTO(
        id: city.id,
        city: city.city,
        countryId: city.country?.id,
        lastUpdate: city.lastUpdate ?
            OffsetDateTime.ofInstant(city.lastUpdate.toInstant(), ZoneId.systemDefault()) : null
    )
  }

  private City toEntity(CityDTO cityDTO) {
    City city = new City(
        id: cityDTO.id,
        city: cityDTO.city,
    )

    if (cityDTO.countryId != null) {
      Country country = countryService.getCountryById(cityDTO.countryId)
      if (!country) throw new NotFoundException("Country for ID ${cityDTO.countryId} does not exist")
      city.country = country
    }

    if (cityDTO.lastUpdate) city.lastUpdate = Date.from(cityDTO.lastUpdate.toInstant())

    city
  }
}
