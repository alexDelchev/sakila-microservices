package com.example.sakila.module.store;

import com.example.sakila.module.staff.Staff;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;

public class Store {

  private Long id;

  private Staff managerStaff;

  private Long addressId;

  private Date lastUpdate;

  public Store() {}

  public Store(Long id) {
    this.id = id;
  }

  public Store(Long id, Staff managerStaff, Long addressId, Date lastUpdate) {
    this.id = id;
    this.managerStaff = managerStaff;
    this.addressId = addressId;
    this.lastUpdate = lastUpdate;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Staff getManagerStaff() {
    return managerStaff;
  }

  public void setManagerStaff(Staff managerStaff) {
    this.managerStaff = managerStaff;
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
