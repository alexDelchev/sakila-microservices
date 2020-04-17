package com.example.sakila.module.staff;

import java.util.Date;

public class Staff {

  private Long id;

  private String firstName;

  private String lastName;

  private Long addressId;

  private String email;

  private Long storeId;

  private Boolean active;

  private String userName;

  private String password;

  private Date lastUpdate;

  public Staff() {}

  public Staff(Long id) {
    this.id = id;
  }

  public Staff(
      Long id, String firstName, String lastName, Long addressId,
      String email, Long storeId, Boolean active, String userName,
      String password, Date lastUpdate
  ) {
    this.id = id;
    this.firstName = firstName;
    this.lastName = lastName;
    this.addressId = addressId;
    this.email = email;
    this.storeId = storeId;
    this.active = active;
    this.userName = userName;
    this.password = password;
    this.lastUpdate = lastUpdate;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
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

  public Long getAddressId() {
    return addressId;
  }

  public void setAddressId(Long addressId) {
    this.addressId = addressId;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public Long getStoreId() {
    return storeId;
  }

  public void setStoreId(Long storeId) {
    this.storeId = storeId;
  }

  public Boolean getActive() {
    return active;
  }

  public void setActive(Boolean active) {
    this.active = active;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public Date getLastUpdate() {
    return lastUpdate;
  }

  public void setLastUpdate(Date lastUpdate) {
    this.lastUpdate = lastUpdate;
  }
}
