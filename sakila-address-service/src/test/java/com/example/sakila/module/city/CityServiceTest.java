package com.example.sakila.module.city;

import com.example.sakila.module.city.repository.CityRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CityServiceTest {

  @Mock
  private CityRepository cityRepository;

  @InjectMocks
  private CityService cityService;

  @Test
  void getCityById() {
    City city = cityService.getCityById(null);

    assertNull(city);
  }

  @Test
  void getCityByAddressId() {
    City city = cityService.getCityByAddressId(null);

    assertNull(city);
  }

  @Test
  void getCitiesByCountryId() {
    List<City> cities = cityService.getCitiesByCountry(null);

    assertNull(cities);
  }
}
