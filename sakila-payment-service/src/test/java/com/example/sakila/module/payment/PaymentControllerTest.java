package com.example.sakila.module.payment;

import com.example.sakila.module.customer.CustomerService;
import com.example.sakila.module.rental.RentalService;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class PaymentControllerTest {

  @Mock
  private PaymentService paymentService;

  @Mock
  private RentalService rentalService;

  @Mock
  private CustomerService customerService;

  @InjectMocks
  private PaymentController paymentController;
  
}
