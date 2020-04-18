package com.example.sakila.event.store;

import java.util.Date;
import java.util.UUID;

public class EventStoreItemDatabaseDTO {

  private UUID eventId;

  private Long aggregateId;

  private Long aggregateVersion;

  private String eventJson;

  private String metaDataJson;

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

  public String getEventJson() {
    return eventJson;
  }

  public void setEventJson(String eventJson) {
    this.eventJson = eventJson;
  }

  public String getMetaDataJson() {
    return metaDataJson;
  }

  public void setMetaDataJson(String metaDataJson) {
    this.metaDataJson = metaDataJson;
  }

  public Date getLastUpdate() {
    return lastUpdate;
  }

  public void setLastUpdate(Date lastUpdate) {
    this.lastUpdate = lastUpdate;
  }
}
