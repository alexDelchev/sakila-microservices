package com.example.sakila.module.payment.event;

import com.example.sakila.module.payment.Payment;
import com.example.sakila.module.payment.event.model.PaymentEventDTO;

public class PaymentEventUtils {

  public static PaymentEventDTO toDTO(Payment payment) {
    PaymentEventDTO dto = new PaymentEventDTO();

    dto.setId(payment.getId());
    dto.setAmount(payment.getAmount());
    dto.setPaymentDate(payment.getPaymentDate());
    dto.setStaffId(payment.getStaffId());

    if (payment.getCustomer() != null) {
      dto.setCustomerId(payment.getCustomer().getId());
    } else {
      throw new IllegalArgumentException(String.format("Payment with id %d has no customer", payment.getId()));
    }

    if (payment.getRental() != null) {
      dto.setRentalId(payment.getRental().getId());
    } else {
      throw new IllegalArgumentException(String.format("Payment with id %d has no rental", payment.getId()));
    }

    return dto;
  }
}
