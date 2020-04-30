package com.example.sakila.module.payment.event.model;

public class PaymentDeletedEvent {

  private Long paymentId;

  public Long getPaymentId() {
    return paymentId;
  }

  public void setPaymentId(Long paymentId) {
    this.paymentId = paymentId;
  }
}
