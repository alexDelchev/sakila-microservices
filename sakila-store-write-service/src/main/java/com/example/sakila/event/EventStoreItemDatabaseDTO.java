package com.example.sakila.event;

import java.util.Date;
import java.util.UUID;

class EventStoreItemDatabaseDTO {

  private UUID eventId;

  private Long aggregateId;

  private Long aggregateVersion;

  private String eventJson;

  private String metaDataJson;

  private Date lastUpdate;

  UUID getEventId() {
    return eventId;
  }

  void setEventId(UUID eventId) {
    this.eventId = eventId;
  }

  Long getAggregateId() {
    return aggregateId;
  }

  void setAggregateId(Long aggregateId) {
    this.aggregateId = aggregateId;
  }

  public Long getAggregateVersion() {
    return aggregateVersion;
  }

  public void setAggregateVersion(Long aggregateVersion) {
    this.aggregateVersion = aggregateVersion;
  }

  String getEventJson() {
    return eventJson;
  }

  void setEventJson(String eventJson) {
    this.eventJson = eventJson;
  }

  String getMetaDataJson() {
    return metaDataJson;
  }

  void setMetaDataJson(String metaDataJson) {
    this.metaDataJson = metaDataJson;
  }

  Date getLastUpdate() {
    return lastUpdate;
  }

  void setLastUpdate(Date lastUpdate) {
    this.lastUpdate = lastUpdate;
  }
}
