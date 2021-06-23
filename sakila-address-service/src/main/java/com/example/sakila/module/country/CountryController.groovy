package com.example.sakila.module.country

import com.example.sakila.generated.server.api.CountriesApi
import com.example.sakila.generated.server.model.CountryDTO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

import java.time.OffsetDateTime
import java.time.ZoneId

@RestController
class CountryController implements CountriesApi {

  private final CountryService countryService

  @Autowired
  CountryController(CountryService countryService) {
    this.countryService = countryService
  }

  @Override
  ResponseEntity<List<CountryDTO>> getAllCountries() {
    ResponseEntity.ok(
        countryService.getAllCountries().collect { toDTO(it) }
    )
  }

  @Override
  ResponseEntity<CountryDTO> getCountryById(@PathVariable("id") Long id) {
    ResponseEntity.ok(toDTO(countryService.getCountryById(id)))
  }

  @Override
  ResponseEntity<CountryDTO> createCountry(@RequestBody CountryDTO countryDTO) {
    ResponseEntity.ok(toDTO(countryService.createCountry(toEntity(countryDTO))))
  }

  @Override
  ResponseEntity<CountryDTO> replaceCountry(@PathVariable("id") Long id, @RequestBody CountryDTO countryDTO) {
    ResponseEntity.ok(toDTO(countryService.updateCountry(id, toEntity(countryDTO))))
  }

  @Override
  ResponseEntity<Void> deleteCountry(@PathVariable("id") Long id) {
    countryService.deleteCountry(id)
    ResponseEntity.ok(null)
  }

  private CountryDTO toDTO(Country country) {
    new CountryDTO(
        country: country.country,
        id: country.id,
        lastUpdate: country.lastUpdate ?
            OffsetDateTime.ofInstant(country.lastUpdate.toInstant(), ZoneId.systemDefault()) : null
    )
  }

  private Country toEntity(CountryDTO countryDTO) {
    Country country = new Country(
        id: countryDTO.id,
        country: countryDTO.country
    )

    if (!countryDTO.lastUpdate) country.setLastUpdate(Date.from(countryDTO.getLastUpdate().toInstant()))
    country
  }

}
