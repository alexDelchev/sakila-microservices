package com.example.sakila.data.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ProcessedEventService {

  private final ProcessedEventRepository repository;

  @Autowired
  public ProcessedEventService(ProcessedEventRepository repository) {
    this.repository = repository;
  }

  public UUID getLatestProcessedEventId() {
    return repository.getLatestProcessedEventId();
  }

  public void markEventAsProcessed(UUID eventId, Long aggregateId, Long aggregateVersion) {
    repository.insertProcessedEvent(eventId, aggregateId, aggregateVersion);
  }

  public Long getAggregateVersion(Long aggregateId) {
    Long version = repository.getAggregateVersion(aggregateId);
    if (version == null) version = 0L;

    return version;
  }

  public Boolean isEventProcessed(UUID eventId) {
    return repository.processedEventExists(eventId);
  }
}
