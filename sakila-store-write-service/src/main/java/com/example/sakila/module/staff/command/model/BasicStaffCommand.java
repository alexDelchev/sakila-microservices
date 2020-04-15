package com.example.sakila.module.staff.command.model;

public abstract class BasicStaffCommand<T> {

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
