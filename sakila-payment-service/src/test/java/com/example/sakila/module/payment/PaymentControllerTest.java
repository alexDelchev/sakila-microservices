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
    final long nonExistingRentalId = -1L;
    when(rentalService.getRentalById(nonExistingRentalId)).thenReturn(null);

    final long existingRentalId = 1L;
    when(rentalService.getRentalById(existingRentalId)).thenReturn(new Rental());

    final long nonExistingCustomerId = -1L;
    when(customerService.getCustomerById(nonExistingCustomerId)).thenReturn(null);

    final long existingCustomerId = 1L;
    when(customerService.getCustomerById(existingCustomerId)).thenReturn(new Customer());

    when(paymentService.createPayment(any(Payment.class))).thenReturn(new Payment());

    PaymentDTO paymentWithValidRental = new PaymentDTO();
    paymentWithValidRental.setCustomerId(existingCustomerId);
    paymentWithValidRental.setRentalId(existingRentalId);

    PaymentDTO paymentWithInvalidRental = new PaymentDTO();
    paymentWithInvalidRental.setCustomerId(existingCustomerId);
    paymentWithInvalidRental.setRentalId(nonExistingRentalId);

    PaymentDTO paymentWithValidCustomer = new PaymentDTO();
    paymentWithValidCustomer.setCustomerId(existingCustomerId);

    PaymentDTO paymentWithInvalidCustomer = new PaymentDTO();
    paymentWithInvalidCustomer.setCustomerId(nonExistingCustomerId);

    assertDoesNotThrow(() -> paymentController.createPayment(paymentWithValidRental));
    assertThrows(NotFoundException.class, () -> paymentController.createPayment(paymentWithInvalidRental));

    assertDoesNotThrow(() -> paymentController.createPayment(paymentWithValidCustomer));
    assertThrows(NotFoundException.class, () -> paymentController.createPayment(paymentWithInvalidCustomer));
  }

  @Test
  void replacePayment() {
    final long nonExistingRentalId = -1L;
    when(rentalService.getRentalById(nonExistingRentalId)).thenReturn(null);

    final long existingRentalId = 1L;
    when(rentalService.getRentalById(existingRentalId)).thenReturn(new Rental());

    final long nonExistingCustomerId = -1L;
    when(customerService.getCustomerById(nonExistingCustomerId)).thenReturn(null);

    final long existingCustomerId = 1L;
    when(customerService.getCustomerById(existingCustomerId)).thenReturn(new Customer());

    when(paymentService.updatePayment(any(Long.class), any(Payment.class))).thenReturn(new Payment());

    PaymentDTO paymentWithValidRental = new PaymentDTO();
    paymentWithValidRental.setCustomerId(existingCustomerId);
    paymentWithValidRental.setRentalId(existingRentalId);

    PaymentDTO paymentWithInvalidRental = new PaymentDTO();
    paymentWithInvalidRental.setCustomerId(existingCustomerId);
    paymentWithInvalidRental.setRentalId(nonExistingRentalId);

    PaymentDTO paymentWithValidCustomer = new PaymentDTO();
    paymentWithValidCustomer.setCustomerId(existingCustomerId);

    PaymentDTO paymentWithInvalidCustomer = new PaymentDTO();
    paymentWithInvalidCustomer.setCustomerId(nonExistingCustomerId);

    assertDoesNotThrow(() -> paymentController.replacePayment(1L, paymentWithValidRental));
    assertThrows(NotFoundException.class, () -> paymentController.replacePayment(1L, paymentWithInvalidRental));

    assertDoesNotThrow(() -> paymentController.replacePayment(1L, paymentWithValidCustomer));
    assertThrows(NotFoundException.class, () -> paymentController.replacePayment(1L, paymentWithInvalidCustomer));
  }
}
