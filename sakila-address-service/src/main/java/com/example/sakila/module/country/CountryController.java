package com.example.sakila.module.country;

import com.example.sakila.generated.server.api.CountriesApi;
import com.example.sakila.generated.server.model.CountryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.time.OffsetDateTime;
import java.time.ZoneId;
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

  private CountryDTO toDTO(Country country) {
    CountryDTO countryDTO = new CountryDTO();
    countryDTO.setId(country.getId());
    countryDTO.setCountry(country.getCountry());
    countryDTO.setLastUpdate(OffsetDateTime.ofInstant(country.getLastUpdate().toInstant(), ZoneId.systemDefault()));
    return countryDTO;
  }

}
