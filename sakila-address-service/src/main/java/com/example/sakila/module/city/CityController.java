package com.example.sakila.module.city;

import com.example.sakila.generated.server.api.CityApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class CityController implements CityApi {

  private final CityService cityService;

  @Autowired
  public CityController(CityService cityService) {
    this.cityService = cityService;
  }
}
