package com.example.sakila.module.rental;

import com.example.sakila.exception.NotFoundException;
import com.example.sakila.generated.server.model.RentalDTO;
import com.example.sakila.module.customer.Customer;
import com.example.sakila.module.customer.CustomerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RentalControllerTest {

  @Mock
  private RentalService rentalService;

  @Mock
  private CustomerService customerService;

  @InjectMocks
  private RentalController rentalController;

  @Test
  void createRental() {
    final long EXISTING_CUSTOMER_ID = 1L;
    when(customerService.getCustomerById(EXISTING_CUSTOMER_ID)).thenReturn(new Customer());

    final long NON_EXISTING_CUSTOMER_ID = -1L;

    when(rentalService.createRental(any(Rental.class))).thenReturn(new Rental());

    RentalDTO validRental = new RentalDTO();
    validRental.setCustomerId(EXISTING_CUSTOMER_ID);
    assertDoesNotThrow(() -> rentalController.createRental(validRental));

    RentalDTO invalidRental = new RentalDTO();
    invalidRental.setCustomerId(NON_EXISTING_CUSTOMER_ID);
    assertThrows(NotFoundException.class, () -> rentalController.createRental(invalidRental));
  }

  @Test
  void replaceRental() {
    final long EXISTING_CUSTOMER_ID = 1L;
    when(customerService.getCustomerById(EXISTING_CUSTOMER_ID)).thenReturn(new Customer());

    final long NON_EXISTING_CUSTOMER_ID = -1L;

    when(rentalService.updateRental(any(Long.class), any(Rental.class))).thenReturn(new Rental());

    RentalDTO validRental = new RentalDTO();
    validRental.setCustomerId(EXISTING_CUSTOMER_ID);
    assertDoesNotThrow(() -> rentalController.replaceRental(1L, validRental));

    RentalDTO invalidRental = new RentalDTO();
    invalidRental.setCustomerId(NON_EXISTING_CUSTOMER_ID);
    assertThrows(NotFoundException.class, () -> rentalController.replaceRental(1L, invalidRental));
  }
}
