package com.example.sakila.module.staff.event.model;

import com.example.sakila.event.Event;
import com.example.sakila.module.staff.StaffWriteModel;

public abstract class BasicStaffEvent<T> extends Event<StaffWriteModel> {

  private Long staffId;

  private T newValue;

  public Long getStaffId() {
    return staffId;
  }

  public void setStaffId(Long staffId) {
    this.staffId = staffId;
  }

  public T getNewValue() {
    return newValue;
  }

  public void setNewValue(T newValue) {
    this.newValue = newValue;
  }
}
