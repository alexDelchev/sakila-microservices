package com.example.sakila.module.staff.event.model;

import com.example.sakila.module.staff.StaffWriteModel;

public class LastNameChangedEvent extends BasicStaffEvent<String> {

  @Override
  public void apply(StaffWriteModel model) {
    model.setLastName(this.getNewValue());
    model.setVersion(this.getVersion());
  }

}
