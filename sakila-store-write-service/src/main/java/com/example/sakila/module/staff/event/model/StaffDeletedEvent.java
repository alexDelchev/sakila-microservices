package com.example.sakila.module.staff.event.model;

import com.example.sakila.event.Event;
import com.example.sakila.module.staff.StaffWriteModel;

public class StaffDeletedEvent extends Event<StaffWriteModel> {

  private Long staffId;

  @Override
  public void apply(StaffWriteModel model) {
    model.setVersion(version);
    model.setFirstName(null);
    model.setLastName(null);
    model.setEmail(null);
    model.setAddress_id(null);
    model.setStoreId(null);
    model.setUserName(null);
    model.setPassword(null);
    model.setActive(null);
  }

  public Long getStaffId() {
    return staffId;
  }

  public void setStaffId(Long staffId) {
    this.staffId = staffId;
  }
}
