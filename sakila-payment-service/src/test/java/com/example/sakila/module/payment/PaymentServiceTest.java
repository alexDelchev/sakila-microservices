package com.example.sakila.module.payment;

import com.example.sakila.module.payment.repository.PaymentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

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
}
