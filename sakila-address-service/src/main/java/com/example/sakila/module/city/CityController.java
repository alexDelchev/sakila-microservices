package com.example.sakila.module.city;

import com.example.sakila.generated.server.api.CityApi;
import com.example.sakila.generated.server.model.CityDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.time.OffsetDateTime;
import java.time.ZoneId;

@Controller
public class CityController implements CityApi {

  private final CityService cityService;

  @Autowired
  public CityController(CityService cityService) {
    this.cityService = cityService;
  }

  private CityDTO toDTO(City city) {
    CityDTO result = new CityDTO();
    BeanUtils.copyProperties(result, city);
    result.setCountryId(city.getCountry().getId());
    result.setLastUpdate(OffsetDateTime.ofInstant(city.getLastUpdate().toInstant(), ZoneId.systemDefault()));
    return result;
  }
}
