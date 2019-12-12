package com.example.sakila.module.country;

import com.example.sakila.exception.NotFoundException;
import com.example.sakila.module.country.repository.CountryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

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

  @Test
  void updateCountry() {
    final long EXISTING_COUNTRY_ID = 1L;
    when(countryRepository.getCountryById(EXISTING_COUNTRY_ID)).thenReturn(new Country());

    final long NON_EXISTING_COUNTRY_ID = -1L;
    when(countryRepository.getCountryById(NON_EXISTING_COUNTRY_ID)).thenReturn(null);

    when(countryRepository.updateCountry(any(Country.class))).thenReturn(new Country());

    assertDoesNotThrow(() -> countryService.updateCountry(EXISTING_COUNTRY_ID, new Country()));
    assertThrows(NotFoundException.class, () -> countryService.updateCountry(NON_EXISTING_COUNTRY_ID, new Country()));
  }

  @Test
  void deleteCountry() {
    final long NON_EXISTING_COUNTRY_ID = -1L;

    assertThrows(NotFoundException.class, () -> countryService.deleteCountry(NON_EXISTING_COUNTRY_ID));
  }
}
