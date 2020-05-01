package com.example.sakila.event.external.rental.model;

public class RentalDeletedEvent {

  private Long rentalId;

  public Long getRentalId() {
    return rentalId;
  }

  public void setRentalId(Long rentalId) {
    this.rentalId = rentalId;
  }
}
