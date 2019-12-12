package com.example.sakila.module.country;

import com.example.sakila.exception.NotFoundException;
import com.example.sakila.generated.server.api.CountriesApi;
import com.example.sakila.generated.server.model.CountryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class CountryController implements CountriesApi {

  private final CountryService countryService;

  @Autowired
  public CountryController(CountryService countryService) {
    this.countryService = countryService;
  }

  @Override
  public ResponseEntity<List<CountryDTO>> getAllCountries() {
    return ResponseEntity.ok(
        countryService.getAllCountries().stream().map(this::toDTO).collect(Collectors.toList())
    );
  }

  @Override
  public ResponseEntity<CountryDTO> getCountryById(@PathVariable("id") Long id) {
    return ResponseEntity.ok(toDTO(countryService.getCountryById(id)));
  }

  @Override
  public ResponseEntity<CountryDTO> addNewCountry(@RequestBody CountryDTO countryDTO) {
    return ResponseEntity.ok(toDTO(countryService.addNewCountry(toEntity(countryDTO))));
  }

  @Override
  public ResponseEntity<CountryDTO> replaceCountry(@PathVariable("id") Long id, @RequestBody CountryDTO countryDTO) {
    return ResponseEntity.ok(toDTO(countryService.updateCountry(id, toEntity(countryDTO))));
  }

  @Override
  public ResponseEntity<Void> deleteCountry(@PathVariable("id") Long id) {
    countryService.deleteCountry(id);
    return ResponseEntity.ok(null);
  }

  private CountryDTO toDTO(Country country) {
    CountryDTO countryDTO = new CountryDTO();
    countryDTO.setId(country.getId());
    countryDTO.setCountry(country.getCountry());
    countryDTO.setLastUpdate(OffsetDateTime.ofInstant(country.getLastUpdate().toInstant(), ZoneId.systemDefault()));
    return countryDTO;
  }

  private Country toEntity(CountryDTO countryDTO) {
    Country country = new Country();
    country.setId(countryDTO.getId());
    country.setCountry(countryDTO.getCountry());
    if (countryDTO.getLastUpdate() != null) country.setLastUpdate(Date.from(countryDTO.getLastUpdate().toInstant()));
    return country;
  }

}
