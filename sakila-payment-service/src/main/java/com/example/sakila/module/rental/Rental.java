package com.example.sakila.module.rental;

import com.example.sakila.module.customer.Customer;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;

@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "rental")
public class Rental {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "rental_id")
  private Long id;

  @Column(name = "rental_date")
  @Temporal(TemporalType.TIMESTAMP)
  private Date rentalDate;

  @Column(name = "film_id")
  private String filmId;

  @Column(name = "store_id")
  private Long storeId;

  @ManyToOne
  @JoinColumn(name = "customer_id")
  private Customer customer;

  @Column(name = "return_date")
  @Temporal(TemporalType.TIMESTAMP)
  private Date returnDate;

  @Column(name = "staff_id")
  private Long staffId;

  @Column(name = "last_update")
  @Temporal(TemporalType.TIMESTAMP)
  private Date lastUpdate;

  public Rental() {
  }

  public Rental(Long id) {
    this.id = id;
  }

  public Rental(
      Long id, Date rentalDate, String filmId, Long storeId, Customer customer, Date returnDate, Long staffId, Date lastUpdate
  ) {
    this.id = id;
    this.rentalDate = rentalDate;
    this.filmId = filmId;
    this.storeId = storeId;
    this.customer = customer;
    this.returnDate = returnDate;
    this.staffId = staffId;
    this.lastUpdate = lastUpdate;
  }

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

  public Customer getCustomer() {
    return customer;
  }

  public void setCustomer(Customer customer) {
    this.customer = customer;
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
