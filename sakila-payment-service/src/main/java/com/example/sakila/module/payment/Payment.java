package com.example.sakila.module.payment;

import javax.persistence.*;
import java.util.Date;

import com.example.sakila.module.rental.Rental;
import com.example.sakila.module.customer.Customer;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "payment")
public class Payment {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "payment_id")
  private Long id;

  @ManyToOne
  @JoinColumn(name = "customer_id")
  private Customer customer;

  @Column(name = "staff_id")
  private Long staffId;

  @OneToOne
  @JoinColumn(name = "rental_id")
  private Rental rental;

  @Column(name = "amount")
  private Float amount;

  @Column(name = "payment_date")
  @Temporal(TemporalType.TIMESTAMP)
  private Date paymentDate;

  public Payment() {}

  public Payment(Long id) {
    this.id = id;
  }

  public Payment(Long id, Customer customer, Long staffId, Rental rental, Float amount, Date paymentDate) {
    this.id = id;
    this.customer = customer;
    this.staffId = staffId;
    this.rental = rental;
    this.amount = amount;
    this.paymentDate = paymentDate;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Customer getCustomer() {
    return customer;
  }

  public void setCustomer(Customer customer) {
    this.customer = customer;
  }

  public Long getStaffId() {
    return staffId;
  }

  public void setStaffId(Long staffId) {
    this.staffId = staffId;
  }

  public Rental getRental() {
    return rental;
  }

  public void setRental(Rental rental) {
    this.rental = rental;
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
