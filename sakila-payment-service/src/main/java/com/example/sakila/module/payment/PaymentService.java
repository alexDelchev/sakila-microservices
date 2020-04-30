package com.example.sakila.module.payment;

import com.example.sakila.event.bus.EventBus;
import com.example.sakila.exception.DataConflictException;
import com.example.sakila.exception.NotFoundException;
import com.example.sakila.module.payment.event.PaymentEventUtils;
import com.example.sakila.module.payment.event.model.PaymentCreatedEvent;
import com.example.sakila.module.payment.event.model.PaymentDeletedEvent;
import com.example.sakila.module.payment.event.model.PaymentEventDTO;
import com.example.sakila.module.payment.event.model.PaymentUpdatedEvent;
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

  private void generateCreatedEvent(Payment payment) {
    PaymentEventDTO dto = PaymentEventUtils.toDTO(payment);
    PaymentCreatedEvent createdEvent = new PaymentCreatedEvent();
    createdEvent.setDto(dto);
    eventBus.emit(createdEvent);
  }

  public Payment createPayment(Payment payment) {
    Payment result = paymentRepository.insertPayment(payment);

    generateCreatedEvent(result);

    return result;
  }

  private void generateUpdatedEvent(Payment payment) {
    PaymentEventDTO dto = PaymentEventUtils.toDTO(payment);
    PaymentUpdatedEvent updatedEvent = new PaymentUpdatedEvent();
    updatedEvent.setDto(dto);
    eventBus.emit(updatedEvent);
  }

  public Payment updatePayment(Long id, Payment source) {
    Payment target = getPaymentById(id);
    if (target == null) throw new NotFoundException("Payment for ID " + id + " does not exist");

    target.setCustomer(source.getCustomer());
    target.setRental(source.getRental());
    target.setStaffId(source.getStaffId());
    target.setAmount(source.getAmount());
    target.setPaymentDate(source.getPaymentDate());

    Payment result = paymentRepository.updatePayment(target);

    generateUpdatedEvent(result);

    return result;
  }

  private void generateDeletedEvent(Long id) {
    PaymentDeletedEvent event = new PaymentDeletedEvent();
    event.setPaymentId(id);
    eventBus.emit(event);
  }

  public void deletePayment(Long id) {
    Payment payment = getPaymentById(id);
    if (payment == null) throw new NotFoundException("Payment for ID " + id + " does not exist");

    try {
      paymentRepository.deletePayment(payment);
      generateDeletedEvent(id);
    } catch (DataIntegrityViolationException e) {
      throw new DataConflictException(e.getMessage(), e);
    }
  }
}
