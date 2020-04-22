package com.example.sakila.data.staff;

import java.util.UUID;

public class StaffEventMessage {

  private UUID eventId;

  private Long staffVersion;

  private StaffDTO staffDTO;

  public UUID getEventId() {
    return eventId;
  }

  public void setEventId(UUID eventId) {
    this.eventId = eventId;
  }

  public Long getStaffVersion() {
    return staffVersion;
  }

  public void setStaffVersion(Long staffVersion) {
    this.staffVersion = staffVersion;
  }

  public StaffDTO getStaffDTO() {
    return staffDTO;
  }

  public void setStaffDTO(StaffDTO staffDTO) {
    this.staffDTO = staffDTO;
  }
}
