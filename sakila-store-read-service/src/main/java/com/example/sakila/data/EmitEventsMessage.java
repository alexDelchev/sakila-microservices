package com.example.sakila.data;

import java.util.UUID;

public class EmitEventsMessage {

  private UUID eventId;

  public UUID getEventId() {
    return eventId;
  }

  public void setEventId(UUID eventId) {
    this.eventId = eventId;
  }
}
