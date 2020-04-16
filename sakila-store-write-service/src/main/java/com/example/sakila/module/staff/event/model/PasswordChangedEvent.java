package com.example.sakila.module.staff.event.model;

import com.example.sakila.module.staff.StaffWriteModel;

public class PasswordChangedEvent extends BasicStaffEvent<String> {

  @Override
  public void apply(StaffWriteModel model) {
    model.setPassword(this.getNewValue());
    model.setVersion(this.getVersion());
  }
}
