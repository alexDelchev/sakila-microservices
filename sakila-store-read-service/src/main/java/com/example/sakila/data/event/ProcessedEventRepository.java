package com.example.sakila.data.event;

import java.util.UUID;

public interface ProcessedEventRepository {

  UUID getLatestProcessedEventId();

  void insertProcessedEvent(UUID eventId, Long aggregateId, Long aggregateVersion);

  Boolean processedEventExists(UUID eventId);

  Long getAggregateVersion(Long aggregateId);
}
