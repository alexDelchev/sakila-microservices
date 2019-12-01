package com.example.sakila.module.payment.repository;

import com.example.sakila.module.payment.Payment;

import java.util.List;

public interface PaymentRepository {

  Payment getPaymentById(Long id);

  Payment getPaymentByRentalId(Long id);

  List<Payment> getPaymentsByCustomerId(Long id);

  List<Payment> getPaymentsByStaffId(Long id);

}
