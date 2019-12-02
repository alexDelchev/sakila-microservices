package com.example.sakila.module.city;

import com.example.sakila.exception.NotFoundException;
import com.example.sakila.generated.server.api.CitiesApi;
import com.example.sakila.generated.server.model.CityDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class CityController implements CitiesApi {

  private final CityService cityService;

  @Autowired
  public CityController(CityService cityService) {
    this.cityService = cityService;
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

  private CityDTO toDTO(City city) {
    CityDTO result = new CityDTO();
    result.setId(city.getId());
    result.setCity(city.getCity());
    result.setCountryId(city.getCountry().getId());
    result.setLastUpdate(OffsetDateTime.ofInstant(city.getLastUpdate().toInstant(), ZoneId.systemDefault()));
    return result;
  }
}
