package com.example.sakila.module.store.command.model;

public class CreateStoreCommand {

  private Long managerStaffID;

  private Long addressId;

  public Long getManagerStaffID() {
    return managerStaffID;
  }

  public void setManagerStaffID(Long managerStaffID) {
    this.managerStaffID = managerStaffID;
  }

  public Long getAddressId() {
    return addressId;
  }

  public void setAddressId(Long addressId) {
    this.addressId = addressId;
  }
}
