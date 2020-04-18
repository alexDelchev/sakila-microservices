package com.example.sakila.module.staff.event;

import com.example.sakila.module.staff.StaffDTO;

import java.util.UUID;

public class StaffEventMessage {

  private UUID eventId;

  private StaffDTO staffDTO;

  public UUID getEventId() {
    return eventId;
  }

  public void setEventId(UUID eventId) {
    this.eventId = eventId;
  }

  public StaffDTO getStaffDTO() {
    return staffDTO;
  }

  public void setStaffDTO(StaffDTO staffDTO) {
    this.staffDTO = staffDTO;
  }
}
