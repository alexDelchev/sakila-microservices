package com.example.sakila.module.city;

import com.example.sakila.generated.server.api.CityApi;
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
public class CityController implements CityApi {

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
  public ResponseEntity<CityDTO> getCityById(@PathVariable("id") Long id) {
    return ResponseEntity.ok(toDTO(cityService.getCityById(id)));
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
