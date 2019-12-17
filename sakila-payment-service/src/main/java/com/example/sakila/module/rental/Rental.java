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

  @Column(name = "inventory_id")
  private Long inventory_id;

  @ManyToOne
  @JoinColumn(name = "customer_id")
  private Customer customer;

  @Column(name = "return_date")
  @Temporal(TemporalType.TIMESTAMP)
  private Date returnDate;

  @Column(name = "staff_id")
  private Long staff_id;

  @Column(name = "last_update")
  @Temporal(TemporalType.TIMESTAMP)
  private Date lastUpdate;

  public Rental() {
  }

  public Rental(Long id) {
    this.id = id;
  }

  public Rental(
      Long id, Date rentalDate, Long inventory_id, Customer customer, Date returnDate, Long staff_id, Date lastUpdate
  ) {
    this.id = id;
    this.rentalDate = rentalDate;
    this.inventory_id = inventory_id;
    this.customer = customer;
    this.returnDate = returnDate;
    this.staff_id = staff_id;
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

  public Long getInventory_id() {
    return inventory_id;
  }

  public void setInventory_id(Long inventory_id) {
    this.inventory_id = inventory_id;
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

  public Long getStaff_id() {
    return staff_id;
  }

  public void setStaff_id(Long staff_id) {
    this.staff_id = staff_id;
  }

  public Date getLastUpdate() {
    return lastUpdate;
  }

  public void setLastUpdate(Date lastUpdate) {
    this.lastUpdate = lastUpdate;
  }
}
