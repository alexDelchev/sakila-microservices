package com.example.sakila.data.event;

import java.util.UUID;

public interface ProcessedEventRepository {

  void insertProcessedEvent(UUID eventId, Long aggregateId, Long aggregateVersion);

  Boolean processedEventExists(UUID eventId);
}
