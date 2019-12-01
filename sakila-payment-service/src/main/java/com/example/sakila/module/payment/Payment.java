package com.example.sakila.module.payment;

import javax.persistence.*;
import java.util.Date;

import com.example.sakila.module.rental.Rental;
import com.example.sakila.module.customer.Customer;

@Entity
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
  private Long staff_id;

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

  public Payment(Long id, Customer customer, Long staff_id, Rental rental, Float amount, Date paymentDate) {
    this.id = id;
    this.customer = customer;
    this.staff_id = staff_id;
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

  public Long getStaff_id() {
    return staff_id;
  }

  public void setStaff_id(Long staff_id) {
    this.staff_id = staff_id;
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
