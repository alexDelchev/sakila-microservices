package com.example.sakila.module.city;

import com.example.sakila.exception.NotFoundException;
import com.example.sakila.generated.server.model.CityDTO;
import com.example.sakila.module.country.CountryService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CityControllerTest {

  @Mock
  private CountryService countryService;

  @Mock
  private CityService cityService;

  @InjectMocks
  private CityController cityController;

  @Test
  void addNewCity() {
    final long NON_EXISTING_COUNTRY_ID = -1L;
    CityDTO invalidDTO = new CityDTO();
    invalidDTO.setCountryId(NON_EXISTING_COUNTRY_ID);

    assertThrows(NotFoundException.class, () -> cityController.addNewCity(invalidDTO));
  }

  @Test
  void replaceCity() {
    final long UPDATED_CITY_ID = 1L;
    final long NON_EXISTING_COUNTRY_ID = -1L;
    CityDTO invalidDTO = new CityDTO();
    invalidDTO.setCountryId(NON_EXISTING_COUNTRY_ID);

    assertThrows(NotFoundException.class, () -> cityController.replaceCity(UPDATED_CITY_ID, invalidDTO));
  }
}
