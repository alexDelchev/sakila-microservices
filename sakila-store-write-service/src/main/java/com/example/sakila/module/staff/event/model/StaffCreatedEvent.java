package com.example.sakila.module.staff.event.model;

import com.example.sakila.event.Event;
import com.example.sakila.module.staff.StaffWriteModel;

public class StaffCreatedEvent extends Event<StaffWriteModel> {

  private Long staffId;

  private String firstName;

  private String lastName;

  private Long address_id;

  private String email;

  private Long storeId;

  private Boolean active;

  private String userName;

  private String password;

  @Override
  public void apply(StaffWriteModel model) {
    model.setId(staffId);
    model.setVersion(version);
    model.setFirstName(firstName);
    model.setLastName(lastName);
    model.setAddress_id(address_id);
    model.setEmail(email);
    model.setStoreId(storeId);
    model.setActive(active);
    model.setUserName(userName);
    model.setPassword(password);
  }

  public Long getStaffId() {
    return staffId;
  }

  public void setStaffId(Long id) {
    this.staffId = id;
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

  public Long getAddress_id() {
    return address_id;
  }

  public void setAddress_id(Long address_id) {
    this.address_id = address_id;
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
}
