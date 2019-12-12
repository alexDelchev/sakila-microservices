package com.example.sakila.module.address;

import com.example.sakila.module.city.CityService;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.*;

class AddressControllerTest {

  @Mock
  private CityService cityService;

  @InjectMocks
  private AddressController addressController;

}
