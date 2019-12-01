package com.example.sakila.module.payment;

import com.example.sakila.module.payment.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentService {

  private final PaymentRepository paymentRepository;

  @Autowired
  public PaymentService(PaymentRepository paymentRepository) {
    this.paymentRepository = paymentRepository;
  }

  public Payment getPaymentById(Long id) {
    if (id == null) return null;
    return paymentRepository.getPaymentById(id);
  }

  public Payment getPaymentByRentalId(Long id) {
    if (id == null) return null;
    return paymentRepository.getPaymentByRentalId(id);
  }

  public List<Payment> getPaymentsByCustomerId(Long id) {
    if (id == null) return null;
    return paymentRepository.getPaymentsByCustomerId(id);
  }

  public List<Payment> getPaymentsByStaffId(Long id) {
    if (id == null) return null;
    return paymentRepository.getPaymentsByStaffId(id);
  }
}
