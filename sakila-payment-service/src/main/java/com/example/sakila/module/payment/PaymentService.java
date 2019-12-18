package com.example.sakila.module.payment;

import com.example.sakila.exception.NotFoundException;
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

  public List<Payment> getPaymentsByRentalId(Long id) {
    if (id == null) return null;
    return paymentRepository.getPaymentsByRentalId(id);
  }

  public List<Payment> getPaymentsByCustomerId(Long id) {
    if (id == null) return null;
    return paymentRepository.getPaymentsByCustomerId(id);
  }

  public List<Payment> getPaymentsByStaffId(Long id) {
    if (id == null) return null;
    return paymentRepository.getPaymentsByStaffId(id);
  }

  public Payment createPayment(Payment payment) {
    return paymentRepository.insertPayment(payment);
  }

  public Payment updatePayment(Long id, Payment source) {
    Payment target = getPaymentById(id);
    if (target == null) throw new NotFoundException("Payment for ID " + id + " does not exist");

    target.setCustomer(source.getCustomer());
    target.setRental(source.getRental());
    target.setStaff_id(source.getStaff_id());
    target.setAmount(source.getAmount());
    target.setPaymentDate(source.getPaymentDate());

    return paymentRepository.updatePayment(target);
  }
}
