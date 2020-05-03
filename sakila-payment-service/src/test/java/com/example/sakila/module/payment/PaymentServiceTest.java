package com.example.sakila.module.payment;

import com.example.sakila.exception.NotFoundException;
import com.example.sakila.module.payment.repository.PaymentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PaymentServiceTest {

  @Mock
  private PaymentRepository paymentRepository;

  @InjectMocks
  private PaymentService paymentService;

  @Test
  void getPaymentById() {
    Payment payment = paymentService.getPaymentById(null);

    assertNull(payment);
  }

  @Test
  void getPaymentsByCustomerId() {
    List<Payment> payments = paymentService.getPaymentsByCustomerId(null);

    assertNull(payments);
  }

  @Test
  void getPaymentsByStaffId() {
    List<Payment> payments = paymentService.getPaymentsByStaffId(null);

    assertNull(payments);
  }

  @Test
  void getPaymentsByRentalId() {
    List<Payment> payments = paymentService.getPaymentsByRentalId(null);

    assertNull(payments);
  }

  @Test
  void updatePayment() {
    final long existingPaymentId = 1L;
    when(paymentRepository.getPaymentById(existingPaymentId)).thenReturn(new Payment());

    final long nonExistingPaymentId = -1L;
    when(paymentRepository.getPaymentById(nonExistingPaymentId)).thenReturn(null);

    when(paymentRepository.updatePayment(any(Payment.class))).thenReturn(new Payment());

    assertDoesNotThrow(() -> paymentService.updatePayment(existingPaymentId, new Payment()));
    assertThrows(NotFoundException.class, () -> paymentService.updatePayment(nonExistingPaymentId, new Payment()));
  }

  @Test
  void deletePayment() {
    final long nonExistingPaymentId = -1L;

    assertThrows(NotFoundException.class, () -> paymentService.deletePayment(nonExistingPaymentId));
  }
}
