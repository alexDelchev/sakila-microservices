package com.example.sakila.module.customer;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;

@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "customer")
public class Customer {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "customer_id")
  private Long id;

  @Column(name = "store_id")
  private Long storeId;

  @Column(name = "first_name")
  private String firstName;

  @Column(name = "last_name")
  private String lastName;

  @Column(name = "email")
  private String email;

  @Column(name = "address_id")
  private Long addressId;

  @Column(name = "activebool")
  private Boolean activeBool;

  @Column(name = "create_date")
  @Temporal(TemporalType.DATE)
  private Date createDate;

  @Column(name = "last_update")
  @Temporal(TemporalType.TIMESTAMP)
  private Date lastUpdate;

  @Column(name = "active")
  private Integer active;

  public Customer() {}

  public Customer(Long id) {
    this.id = id;
  }

  public Customer(
      Long id, Long storeId, String firstName, String lastName, String email,
      Long addressId, Boolean activeBool, Date createDate, Date lastUpdate, Integer active
  ) {
    this.id = id;
    this.storeId = storeId;
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.addressId = addressId;
    this.activeBool = activeBool;
    this.createDate = createDate;
    this.lastUpdate = lastUpdate;
    this.active = active;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getStoreId() {
    return storeId;
  }

  public void setStoreId(Long storeId) {
    this.storeId = storeId;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public Long getAddressId() {
    return addressId;
  }

  public void setAddressId(Long addressId) {
    this.addressId = addressId;
  }

  public Boolean getActiveBool() {
    return activeBool;
  }

  public void setActiveBool(Boolean activeBool) {
    this.activeBool = activeBool;
  }

  public Date getCreateDate() {
    return createDate;
  }

  public void setCreateDate(Date createDate) {
    this.createDate = createDate;
  }

  public Date getLastUpdate() {
    return lastUpdate;
  }

  public void setLastUpdate(Date lastUpdate) {
    this.lastUpdate = lastUpdate;
  }

  public Integer getActive() {
    return active;
  }

  public void setActive(Integer active) {
    this.active = active;
  }
}
