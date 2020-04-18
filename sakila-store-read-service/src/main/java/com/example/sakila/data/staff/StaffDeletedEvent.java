package com.example.sakila.data.staff;

import java.util.UUID;

public class StaffDeletedEvent {

  private UUID id;

  private Long version;

  private Long staffId;

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public Long getVersion() {
    return version;
  }

  public void setVersion(Long version) {
    this.version = version;
  }

  public Long getStaffId() {
    return staffId;
  }

  public void setStaffId(Long staffId) {
    this.staffId = staffId;
  }
}
