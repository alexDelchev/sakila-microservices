package com.example.sakila.module.staff.event.model;

import com.example.sakila.module.staff.StaffWriteModel;

public class AddressChangedEvent extends BasicStaffEvent<Long> {

  @Override
  public void apply(StaffWriteModel model) {
    model.setAddress_id(this.getNewValue());
    model.setVersion(this.getVersion());
  }
}
