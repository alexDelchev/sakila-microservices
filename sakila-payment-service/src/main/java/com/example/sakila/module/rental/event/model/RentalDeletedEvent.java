package com.example.sakila.module.rental.event.model;

public class RentalDeletedEvent {

  private Long rentalId;

  public Long getRentalId() {
    return rentalId;
  }

  public void setRentalId(Long rentalId) {
    this.rentalId = rentalId;
  }
}
