package com.example.sakila.module.staff.event.model;

import com.example.sakila.module.staff.StaffWriteModel;

public class EmailChangedEvent extends BasicStaffEvent<String> {

  @Override
  public void apply(StaffWriteModel model) {
    model.setEmail(this.getNewValue());
    model.setVersion(this.getVersion());
  }
}
