package com.example.sakila.module.payment.event.model;

import java.util.Date;

public class PaymentEventDTO {

  private Long id;

  private Long customerId;

  private Long staffId;

  private Long rentalId;

  private Float amount;

  private Date paymentDate;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getCustomerId() {
    return customerId;
  }

  public void setCustomerId(Long customerId) {
    this.customerId = customerId;
  }

  public Long getStaffId() {
    return staffId;
  }

  public void setStaffId(Long staffId) {
    this.staffId = staffId;
  }

  public Long getRentalId() {
    return rentalId;
  }

  public void setRentalId(Long rentalId) {
    this.rentalId = rentalId;
  }

  public Float getAmount() {
    return amount;
  }

  public void setAmount(Float amount) {
    this.amount = amount;
  }

  public Date getPaymentDate() {
    return paymentDate;
  }

  public void setPaymentDate(Date paymentDate) {
    this.paymentDate = paymentDate;
  }
}
