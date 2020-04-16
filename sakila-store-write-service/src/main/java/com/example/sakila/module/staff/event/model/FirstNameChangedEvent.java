package com.example.sakila.module.staff.event.model;

import com.example.sakila.module.staff.StaffWriteModel;

public class FirstNameChangedEvent extends BasicStaffEvent<String> {

  @Override
  public void apply(StaffWriteModel model) {
    model.setFirstName(this.getNewValue());
    model.setVersion(this.getVersion());
  }
}
