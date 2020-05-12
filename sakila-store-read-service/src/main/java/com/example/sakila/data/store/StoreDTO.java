package com.example.sakila.data.store;

import java.time.OffsetDateTime;
import java.util.Date;

public class StoreDTO {

  private Long id;

  private Long version;

  private Long managerStaffId;

  private Long addressId;

  private OffsetDateTime lastUpdate;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getVersion() {
    return version;
  }

  public void setVersion(Long version) {
    this.version = version;
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

  public OffsetDateTime getLastUpdate() {
    return lastUpdate;
  }

  public void setLastUpdate(OffsetDateTime lastUpdate) {
    this.lastUpdate = lastUpdate;
  }
}
