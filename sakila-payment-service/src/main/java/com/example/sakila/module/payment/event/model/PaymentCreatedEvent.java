package com.example.sakila.module.payment.event.model;

public class PaymentCreatedEvent {

  private PaymentEventDTO dto;

  public PaymentEventDTO getDto() {
    return dto;
  }

  public void setDto(PaymentEventDTO dto) {
    this.dto = dto;
  }
}
