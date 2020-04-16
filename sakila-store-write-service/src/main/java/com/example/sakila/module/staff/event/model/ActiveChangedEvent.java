package com.example.sakila.module.staff.event.model;

import com.example.sakila.module.staff.StaffWriteModel;

public class ActiveChangedEvent extends BasicStaffEvent<Boolean> {

  @Override
  public void apply(StaffWriteModel model) {
    model.setActive(this.getNewValue());
    model.setVersion(this.getVersion());
  }
}
