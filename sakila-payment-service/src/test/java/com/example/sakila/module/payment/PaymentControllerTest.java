package com.example.sakila.module.payment;

import com.example.sakila.exception.NotFoundException;
import com.example.sakila.generated.server.model.PaymentDTO;
import com.example.sakila.module.customer.Customer;
import com.example.sakila.module.customer.CustomerService;
import com.example.sakila.module.rental.Rental;
import com.example.sakila.module.rental.RentalService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

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

  @Test
  void createPayment() {
    final long NON_EXISTING_RENTAL_ID = -1L;
    when(rentalService.getRentalById(NON_EXISTING_RENTAL_ID)).thenReturn(null);

    final long EXISTING_RENTAL_ID = 1L;
    when(rentalService.getRentalById(EXISTING_RENTAL_ID)).thenReturn(new Rental());

    final long NON_EXISTING_CUSTOMER_ID = -1L;
    when(customerService.getCustomerById(NON_EXISTING_CUSTOMER_ID)).thenReturn(null);

    final long EXISTING_CUSTOMER_ID = 1L;
    when(customerService.getCustomerById(EXISTING_CUSTOMER_ID)).thenReturn(new Customer());

    when(paymentService.createPayment(any(Payment.class))).thenReturn(new Payment());

    PaymentDTO paymentWithValidRental = new PaymentDTO();
    paymentWithValidRental.setCustomerId(EXISTING_CUSTOMER_ID);
    paymentWithValidRental.setRentalId(EXISTING_RENTAL_ID);

    PaymentDTO paymentWithInvalidRental = new PaymentDTO();
    paymentWithInvalidRental.setCustomerId(EXISTING_CUSTOMER_ID);
    paymentWithInvalidRental.setRentalId(NON_EXISTING_RENTAL_ID);

    PaymentDTO paymentWithValidCustomer = new PaymentDTO();
    paymentWithValidCustomer.setCustomerId(EXISTING_CUSTOMER_ID);

    PaymentDTO paymentWithInvalidCustomer = new PaymentDTO();
    paymentWithInvalidCustomer.setCustomerId(NON_EXISTING_CUSTOMER_ID);

    assertDoesNotThrow(() -> paymentController.createPayment(paymentWithValidRental));
    assertThrows(NotFoundException.class, () -> paymentController.createPayment(paymentWithInvalidRental));

    assertDoesNotThrow(() -> paymentController.createPayment(paymentWithValidCustomer));
    assertThrows(NotFoundException.class, () -> paymentController.createPayment(paymentWithInvalidCustomer));
  }

  @Test
  void replacePayment() {
    final long NON_EXISTING_RENTAL_ID = -1L;
    when(rentalService.getRentalById(NON_EXISTING_RENTAL_ID)).thenReturn(null);

    final long EXISTING_RENTAL_ID = 1L;
    when(rentalService.getRentalById(EXISTING_RENTAL_ID)).thenReturn(new Rental());

    final long NON_EXISTING_CUSTOMER_ID = -1L;
    when(customerService.getCustomerById(NON_EXISTING_CUSTOMER_ID)).thenReturn(null);

    final long EXISTING_CUSTOMER_ID = 1L;
    when(customerService.getCustomerById(EXISTING_CUSTOMER_ID)).thenReturn(new Customer());

    when(paymentService.updatePayment(any(Long.class), any(Payment.class))).thenReturn(new Payment());

    PaymentDTO paymentWithValidRental = new PaymentDTO();
    paymentWithValidRental.setCustomerId(EXISTING_CUSTOMER_ID);
    paymentWithValidRental.setRentalId(EXISTING_RENTAL_ID);

    PaymentDTO paymentWithInvalidRental = new PaymentDTO();
    paymentWithInvalidRental.setCustomerId(EXISTING_CUSTOMER_ID);
    paymentWithInvalidRental.setRentalId(NON_EXISTING_RENTAL_ID);

    PaymentDTO paymentWithValidCustomer = new PaymentDTO();
    paymentWithValidCustomer.setCustomerId(EXISTING_CUSTOMER_ID);

    PaymentDTO paymentWithInvalidCustomer = new PaymentDTO();
    paymentWithInvalidCustomer.setCustomerId(NON_EXISTING_CUSTOMER_ID);

    assertDoesNotThrow(() -> paymentController.replacePayment(1L, paymentWithValidRental));
    assertThrows(NotFoundException.class, () -> paymentController.replacePayment(1L, paymentWithInvalidRental));

    assertDoesNotThrow(() -> paymentController.replacePayment(1L, paymentWithValidCustomer));
    assertThrows(NotFoundException.class, () -> paymentController.replacePayment(1L, paymentWithInvalidCustomer));
  }
}
