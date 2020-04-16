package com.example.sakila.module.store.event.model;

import com.example.sakila.module.store.StoreWriteModel;

public class AddressChangedEvent extends BasicStoreEvent<Long> {

  @Override
  public void apply(StoreWriteModel model) {
    model.setAddressId(this.getNewValue());
    model.setVersion(this.getVersion());
  }
}
