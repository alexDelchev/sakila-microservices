package com.example.sakila.module.store.event.model;

import com.example.sakila.event.Event;
import com.example.sakila.module.store.StoreWriteModel;

public class StoreCreatedEvent extends Event<StoreWriteModel> {

  private Long storeId;

  private Long managerStaffId;

  private Long addressId;

  @Override
  public void apply(StoreWriteModel model) {
    model.setManagerStaffId(managerStaffId);
    model.setAddressId(addressId);
  }

  public Long getStoreId() {
    return storeId;
  }

  public void setStoreId(Long storeId) {
    this.storeId = storeId;
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
}
