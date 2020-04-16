package com.example.sakila.module.staff.event.model;

import com.example.sakila.module.staff.StaffWriteModel;

public class UsernameChangedEvent extends BasicStaffEvent<String> {

  @Override
  public void apply(StaffWriteModel model) {
    model.setUserName(this.getNewValue());
    model.setVersion(this.getVersion());
  }
}
