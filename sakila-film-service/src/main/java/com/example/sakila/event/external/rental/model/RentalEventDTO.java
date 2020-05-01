package com.example.sakila.event.external.rental.model;

import java.util.Date;

public class RentalEventDTO {

  private Long id;

  private Date rentalDate;

  private String filmId;

  private Long storeId;

  private Long customerId;

  private Date returnDate;

  private Long staffId;

  private Date lastUpdate;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Date getRentalDate() {
    return rentalDate;
  }

  public void setRentalDate(Date rentalDate) {
    this.rentalDate = rentalDate;
  }

  public String getFilmId() {
    return filmId;
  }

  public void setFilmId(String filmId) {
    this.filmId = filmId;
  }

  public Long getStoreId() {
    return storeId;
  }

  public void setStoreId(Long storeId) {
    this.storeId = storeId;
  }

  public Long getCustomerId() {
    return customerId;
  }

  public void setCustomerId(Long customerId) {
    this.customerId = customerId;
  }

  public Date getReturnDate() {
    return returnDate;
  }

  public void setReturnDate(Date returnDate) {
    this.returnDate = returnDate;
  }

  public Long getStaffId() {
    return staffId;
  }

  public void setStaffId(Long staffId) {
    this.staffId = staffId;
  }

  public Date getLastUpdate() {
    return lastUpdate;
  }

  public void setLastUpdate(Date lastUpdate) {
    this.lastUpdate = lastUpdate;
  }
}
