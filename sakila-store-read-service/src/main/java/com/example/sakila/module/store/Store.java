package com.example.sakila.module.store;

import java.util.Date;

public class Store {

  private Long id;

  private Long managerStaffId;

  private Long addressId;

  private Date lastUpdate;

  public Store() {}

  public Store(Long id) {
    this.id = id;
  }

  public Store(Long id, Long managerStaffId, Long addressId, Date lastUpdate) {
    this.id = id;
    this.managerStaffId = managerStaffId;
    this.addressId = addressId;
    this.lastUpdate = lastUpdate;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getManagerStaffId() {
    return managerStaffId;
  }

  public void setManagerStaffId(Long managerStaffId) {
    this.managerStaffId = managerStaffId;
  }

  public Long getAddressId() {
    return addressId;
  }

  public void setAddressId(Long addressId) {
    this.addressId = addressId;
  }

  public Date getLastUpdate() {
    return lastUpdate;
  }

  public void setLastUpdate(Date lastUpdate) {
    this.lastUpdate = lastUpdate;
  }
}
