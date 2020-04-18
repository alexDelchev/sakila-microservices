package com.example.sakila.data.event;

import java.util.UUID;

public interface ProcessedEventRepository {

  void insertProcessedEvent(UUID eventId);

  Boolean processedEventExists(UUID eventId);
}
