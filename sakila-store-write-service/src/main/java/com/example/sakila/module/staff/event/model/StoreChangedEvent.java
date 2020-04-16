package com.example.sakila.module.staff.event.model;

import com.example.sakila.module.staff.StaffWriteModel;

public class StoreChangedEvent extends BasicStaffEvent<Long> {

  @Override
  public void apply(StaffWriteModel model) {
    model.setStoreId(this.getNewValue());
    model.setVersion(this.getVersion());
  }
}
