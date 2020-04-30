package com.example.sakila.module.payment;

import com.example.sakila.event.bus.EventBus;
import com.example.sakila.exception.DataConflictException;
import com.example.sakila.exception.NotFoundException;
import com.example.sakila.module.payment.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentService {

  private final EventBus eventBus;

  private final PaymentRepository paymentRepository;

  @Autowired
  public PaymentService(@Qualifier("PaymentEventBus") EventBus eventBus, PaymentRepository paymentRepository) {
    this.eventBus = eventBus;
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
    target.setStaffId(source.getStaffId());
    target.setAmount(source.getAmount());
    target.setPaymentDate(source.getPaymentDate());

    return paymentRepository.updatePayment(target);
  }

  public void deletePayment(Long id) {
    Payment payment = getPaymentById(id);
    if (payment == null) throw new NotFoundException("Payment for ID " + id + " does not exist");

    try {
      paymentRepository.deletePayment(payment);
    } catch (DataIntegrityViolationException e) {
      throw new DataConflictException(e.getMessage(), e);
    }
  }
}
