package com.example.sakila.module.country;

import com.example.sakila.module.country.repository.CountryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CountryServiceTest {

  @Mock
  private CountryRepository countryRepository;

  @InjectMocks
  private CountryService countryService;

  @Test
  void getCountryById() {
    Country country = countryService.getCountryById(null);

    assertNull(country);
  }

  @Test
  void getCountryByAddressId() {
    Country country = countryService.getCountryByAddressId(null);

    assertNull(country);
  }
}
