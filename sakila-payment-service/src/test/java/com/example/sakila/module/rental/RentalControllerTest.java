package com.example.sakila.module.rental;

import com.example.sakila.module.customer.CustomerService;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class RentalControllerTest {

  @Mock
  private RentalService rentalService;

  @Mock
  private CustomerService customerService;
  
}
