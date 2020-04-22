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

  public void markEventAsProcessed(UUID eventId, Long aggregateId, Long aggregateVersion) {
    repository.insertProcessedEvent(eventId, aggregateId, aggregateVersion);
  }
  public Boolean isEventProcessed(UUID eventId) {
    return repository.processedEventExists(eventId);
  }
}
