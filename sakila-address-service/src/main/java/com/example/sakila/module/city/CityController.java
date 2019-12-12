package com.example.sakila.module.city;

import com.example.sakila.exception.NotFoundException;
import com.example.sakila.generated.server.api.CitiesApi;
import com.example.sakila.generated.server.model.CityDTO;
import com.example.sakila.module.country.Country;
import com.example.sakila.module.country.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class CityController implements CitiesApi {

  private final CityService cityService;

  private final CountryService countryService;

  @Autowired
  public CityController(CityService cityService, CountryService countryService) {
    this.cityService = cityService;
    this.countryService = countryService;
  }

  @Override
  public ResponseEntity<List<CityDTO>> getAllCities() {
    return ResponseEntity.ok(
        cityService.getAllCities().stream().map(this::toDTO).collect(Collectors.toList())
    );
  }

  @Override
  public ResponseEntity<List<CityDTO>> getCitiesByCountryId(@PathVariable("id") Long id) {
    List<City> cities = cityService.getCitiesByCountry(id);
    if (cities == null || cities.size() == 0) throw new NotFoundException(
        "Cities for Country ID " + id + " do not exist"
    );

    return ResponseEntity.ok(cities.stream().map(this::toDTO).collect(Collectors.toList()));
  }

  @Override
  public ResponseEntity<CityDTO> getCityById(@PathVariable("id") Long id) {
    City city = cityService.getCityById(id);
    if (city == null) throw new NotFoundException("City for ID " + id + " does not exist");

    return ResponseEntity.ok(toDTO(city));
  }

  @Override
  public ResponseEntity<CityDTO> addNewCity(@RequestBody CityDTO cityDTO) {
    return ResponseEntity.ok(toDTO(cityService.addNewCity(toEntity(cityDTO))));
  }

  @Override
  public ResponseEntity<CityDTO> replaceCity(@PathVariable("id") Long id, @RequestBody CityDTO cityDTO) {
    return ResponseEntity.ok(toDTO(cityService.updateCity(id, toEntity(cityDTO))));
  }

  @Override
  public ResponseEntity<Void> deleteCity(@PathVariable("id") Long id) {
    cityService.deleteCity(id);
    return ResponseEntity.ok(null);
  }

  private CityDTO toDTO(City city) {
    CityDTO result = new CityDTO();
    result.setId(city.getId());
    result.setCity(city.getCity());
    result.setCountryId(city.getCountry().getId());
    result.setLastUpdate(OffsetDateTime.ofInstant(city.getLastUpdate().toInstant(), ZoneId.systemDefault()));
    return result;
  }

  private City toEntity(CityDTO cityDTO) {
    City city = new City();
    city.setId(cityDTO.getId());
    city.setCity(cityDTO.getCity());

    if (cityDTO.getCountryId() != null) {
      Country country = countryService.getCountryById(cityDTO.getCountryId());
      if (country == null) throw new NotFoundException("Country for ID " + cityDTO.getCountryId() + " does not exist");
      city.setCountry(country);
    }

    if (cityDTO.getLastUpdate() != null) city.setLastUpdate(Date.from(cityDTO.getLastUpdate().toInstant()));
    return city;
  }
}
