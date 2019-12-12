package com.example.sakila.module.city;

import com.example.sakila.exception.NotFoundException;
import com.example.sakila.module.city.repository.CityRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

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

  @Test
  void updateCity() {
    final long EXISTING_CITY_ID = 1L;
    when(cityRepository.getCityById(EXISTING_CITY_ID)).thenReturn(new City());

    final long NON_EXISTING_CITY_ID = -1L;
    when(cityRepository.getCityById(NON_EXISTING_CITY_ID)).thenReturn(null);

    when(cityRepository.updateCity(any(City.class))).thenReturn(new City());

    assertDoesNotThrow(() -> cityService.updateCity(EXISTING_CITY_ID, new City()));
    assertThrows(NotFoundException.class, () -> cityService.updateCity(NON_EXISTING_CITY_ID, new City()));
  }
}
