package com.example.sakila.module.store.event.model;

import com.example.sakila.event.Event;
import com.example.sakila.module.store.StoreWriteModel;

public class StoreDeletedEvent extends Event<StoreWriteModel> {

  private Long storeId;

  @Override
  public void apply(StoreWriteModel model) {
    model.setVersion(version);
    model.setAddressId(null);
    model.setManagerStaffId(null);
  }

  public Long getStoreId() {
    return storeId;
  }

  public void setStoreId(Long storeId) {
    this.storeId = storeId;
  }
}
