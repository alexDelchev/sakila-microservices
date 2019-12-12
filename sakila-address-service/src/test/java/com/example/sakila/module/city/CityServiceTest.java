package com.example.sakila.module.city;

import com.example.sakila.module.city.repository.CityRepository;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CityServiceTest {

  @Mock
  private CityRepository cityRepository;

  @InjectMocks
  private CityService cityService;
  
}
