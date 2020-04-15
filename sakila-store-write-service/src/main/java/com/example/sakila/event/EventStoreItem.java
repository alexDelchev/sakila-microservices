package com.example.sakila.event;

import java.util.Date;
import java.util.UUID;

public class EventStoreItem<T> {

  private UUID eventId;

  private Long aggregateId;

  private Long aggregateVersion;

  private Event<T> event;

  private EventMetaData metaData;

  private Date lastUpdate;

  public UUID getEventId() {
    return eventId;
  }

  public void setEventId(UUID eventId) {
    this.eventId = eventId;
  }

  public Long getAggregateId() {
    return aggregateId;
  }

  public void setAggregateId(Long aggregateId) {
    this.aggregateId = aggregateId;
  }

  public Long getAggregateVersion() {
    return aggregateVersion;
  }

  public void setAggregateVersion(Long aggregateVersion) {
    this.aggregateVersion = aggregateVersion;
  }

  public Event<T> getEvent() {
    return event;
  }

  public void setEvent(Event<T> event) {
    this.event = event;
  }

  public EventMetaData getMetaData() {
    return metaData;
  }

  public void setMetaData(EventMetaData metaData) {
    this.metaData = metaData;
  }

  public Date getLastUpdate() {
    return lastUpdate;
  }

  public void setLastUpdate(Date lastUpdate) {
    this.lastUpdate = lastUpdate;
  }
}
