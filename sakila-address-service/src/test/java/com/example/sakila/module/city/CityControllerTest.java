package com.example.sakila.module.city;

import com.example.sakila.module.country.CountryService;
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
  
}
