package com.example.sakila.module.store.event.model;

import com.example.sakila.module.store.StoreWriteModel;

public class ManagerChangedEvent extends BasicStoreEvent<Long> {

  @Override
  public void apply(StoreWriteModel store) {
    store.setManagerStaffId(this.getNewValue());
    store.setVersion(this.getVersion());
  }
}
